package com.m3libea.nytimessearch.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.m3libea.nytimessearch.R;
import com.m3libea.nytimessearch.adapters.ArticleArrayAdapter;
import com.m3libea.nytimessearch.external.EndlessScrollListener;
import com.m3libea.nytimessearch.models.Doc;
import com.m3libea.nytimessearch.models.NYTimesResponse;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.gvResults) GridView gvResults;

    ArrayList<Doc> articles;
    ArticleArrayAdapter adapter;

    String queryS;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        setUpViews();

    }

    public void setUpViews(){
        ButterKnife.bind(this);
        articles = new ArrayList<>();
        adapter = new ArticleArrayAdapter(this, articles);
        gvResults.setAdapter(adapter);

        gvResults.setOnItemClickListener((parent, view, position, id) -> {
            //Create intent
            Intent i = new Intent(getApplicationContext(), ArticleActivity.class);
            //get article to display

            Doc doc = articles.get(position);
            //pass in that article into intent
            i.putExtra("article", Parcels.wrap(doc));
            //launch activity
            startActivity(i);
        });

        gvResults.setOnScrollListener(new EndlessScrollListener(){

            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                apiQuery(queryS, page);

                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();

                clearList();

                apiQuery(query, 0);
                queryS = query;

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_filter) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void apiQuery(String query, int page) {

        if (!isNetworkAvailable()) {
            Toast.makeText(this, "Network not Available", Toast.LENGTH_SHORT).show();
            return;
        }


        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://api.nytimes.com/svc/search/v2/articlesearch.json";
        RequestParams params = new RequestParams();
        params.put("api-key", "7217c0ccb50a41d198c730e132230c0a");
        params.put("page", page);
        params.put("q", query);

        client.get(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Gson gson = new GsonBuilder().create();
                articles.addAll(gson.fromJson(responseString, NYTimesResponse.class).getResponse().getDocs());
                adapter.notifyDataSetChanged();
            }
        });

    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    private void clearList() {
        int size = articles.size();
        articles.clear();
        adapter.notifyDataSetChanged();
    }


}
