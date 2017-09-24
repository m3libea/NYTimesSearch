package com.m3libea.nytimessearch.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.m3libea.nytimessearch.R;
import com.m3libea.nytimessearch.activities.ArticleActivity;
import com.m3libea.nytimessearch.models.Doc;

import org.parceler.Parcels;

import java.util.List;

/**
 * Created by m3libea on 9/23/17.
 */

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder{

       //@BindView(R.id.ivimage)
        ImageView ivImage;
        //@BindView(tvTitle)
        TextView tvTitle;
        //@BindView(tvDesks)
        TextView tvDesks;

        CardView card;

        public ViewHolder(View itemView) {
            super(itemView);

            ivImage = (ImageView) itemView.findViewById(R.id.ivimage);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvDesks = (TextView) itemView.findViewById(R.id.tvDesks);
            card = (CardView) itemView.findViewById(R.id.card);
        }

        public void bind(final Doc article){
            tvTitle.setText(article.getHeadline().getMain());
            tvDesks.setText(article.getNewsDesk());

            String thumbnail = "";

            if (article.getMultimedia().size() > 0){
                thumbnail = "http://www.nytimes.com/" + article.getMultimedia().get(0).getUrl();
            }

            if(!TextUtils.isEmpty(thumbnail)){
                Glide.with(context).load(thumbnail).into(ivImage);
            }

            card.setOnClickListener(view -> {
                //Create intent
                Intent i = new Intent(context, ArticleActivity.class);
                //pass in that article into intent
                i.putExtra("article", Parcels.wrap(article));
                //launch activity
                context.startActivity(i);
            });
        }
    }

    private List<Doc> articles;
    private Context context;

    public ArticlesAdapter(Context context, List<Doc> articles) {
        this.articles = articles;
        this.context = context;
    }

    private Context getContext() {
        return context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View articleView = inflater.inflate(R.layout.item_article_result, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(articleView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ArticlesAdapter.ViewHolder holder, int position) {

        Doc article = articles.get(position);

        holder.bind(article);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }


}
