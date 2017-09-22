package com.m3libea.nytimessearch.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;

import com.m3libea.nytimessearch.NYTimesApplication;
import com.m3libea.nytimessearch.R;
import com.m3libea.nytimessearch.adapters.ArticleArrayAdapter;
import com.m3libea.nytimessearch.api.NYTimesEndpoint;
import com.m3libea.nytimessearch.external.EndlessScrollListener;
import com.m3libea.nytimessearch.fragments.FilterFragment;
import com.m3libea.nytimessearch.models.Doc;
import com.m3libea.nytimessearch.models.SearchQuery;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchActivity extends AppCompatActivity implements FilterFragment.FilterDialogListener{

    @BindView(R.id.gvResults) GridView gvResults;

    ArrayList<Doc> articles;
    ArticleArrayAdapter adapter;

    private NYTimesEndpoint apiService;

    SearchQuery sQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sQuery = new SearchQuery();

        apiService = ((NYTimesApplication)getApplication()).getRetrofit()
                .create(NYTimesEndpoint.class);

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
                sQuery.setPage(page);
                apiQuery();

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

                sQuery.setQuery(query);
                sQuery.setPage(0);
                apiQuery();

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

        switch (id) {
            case R.id.action_filter:
                showFilterDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showFilterDialog() {
        FragmentManager fm = getSupportFragmentManager();
        FilterFragment filterFragment = FilterFragment.newInstance(sQuery);
        filterFragment.show(fm, "fragment_filter");
    }

    public void apiQuery() {

        if (!isNetworkAvailable()) {
            Snackbar bar = Snackbar.make(findViewById(R.id.activity_search), R.string.connection_error, Snackbar.LENGTH_INDEFINITE)
                    .setAction("Retry", v -> apiQuery());
            bar.show();
            return;
        }


        apiService.articleSearch(
                sQuery.getPage(),
                sQuery.getQuery(),
                sQuery.getFormattedDate(),
                sQuery.getFormattedSort(),
                sQuery.getFormattedDesks(),
                NYTimesApplication.getApiKey())
                .flatMapIterable(nyTimesResponse -> nyTimesResponse.getResponse().getDocs())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        doc -> {
                            articles.add(doc);
                            adapter.notifyDataSetChanged();
                        },
                        throwable -> processError(throwable),
                        () -> Log.i("SearchActivity", "done!!!")
                );
    }

    private void processError(Throwable throwable) {

        if (throwable.getMessage().contains("429")){;
            Snackbar bar = Snackbar.make(findViewById(R.id.activity_search), R.string.request_error, Snackbar.LENGTH_SHORT)
                    .setAction("Retry", v -> apiQuery());
            bar.show();
        }
        throwable.printStackTrace();
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


    @Override
    public void onFinishingFilter(SearchQuery q) {
        sQuery.setNewsDesks(q.getNewsDesks());
        sQuery.setBeginDate(q.getBeginDate());
        sQuery.setSort(q.getSort());

        clearList();
        sQuery.setPage(0);
        apiQuery();
    }


}
