package com.m3libea.nytimessearch.adapters;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.m3libea.nytimessearch.R;
import com.m3libea.nytimessearch.models.Doc;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by m3libea on 9/23/17.
 */

public class ArticlesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public class ImageViewHolder extends RecyclerView.ViewHolder{

       @BindView(R.id.ivimage)
        ImageView ivImage;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvDesks)
        TextView tvDesks;
        @BindView(R.id.tvSnippet)
        TextView tvSnippet;
        @BindView(R.id.card)
        CardView card;


        public ImageViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        public void bind(final Doc article){
            tvTitle.setText(article.getHeadline().getMain());
            tvDesks.setText(article.getNewsDesk());
            tvSnippet.setText(article.getSnippet());

            String thumbnail = "http://www.nytimes.com/" + article.getMultimedia().get(0).getUrl();

            Glide.with(context).load(thumbnail).into(ivImage);

            card.setOnClickListener(view -> {

                String url = article.getWebUrl();
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                // set toolbar color and/or setting custom actions before invoking build()
                builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary));
                builder.addDefaultShareMenuItem();

                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_share);
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, url);

                int requestCode = 100;

                PendingIntent pendingIntent = PendingIntent.getActivity(context,
                        requestCode,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                builder.setActionButton(bitmap, "Share Link", pendingIntent, true);


                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(context, Uri.parse(url));
            });
        }
    }


    public class TitleViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvDesks)
        TextView tvDesks;
        @BindView(R.id.tvSnippet)
        TextView tvSnippet;
        @BindView(R.id.card)
        CardView card;

        public TitleViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        public void bind(final Doc article){
            tvTitle.setText(article.getHeadline().getMain());
            tvDesks.setText(article.getNewsDesk());
            tvSnippet.setText(article.getSnippet());

            card.setOnClickListener(view -> {
                String url = article.getWebUrl();
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                // set toolbar color and/or setting custom actions before invoking build()
                builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary));
                builder.addDefaultShareMenuItem();

                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_share);
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, url);

                int requestCode = 100;

                PendingIntent pendingIntent = PendingIntent.getActivity(context,
                        requestCode,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                builder.setActionButton(bitmap, "Share Link", pendingIntent, true);
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(context, Uri.parse(url));
            });
        }
    }

    private List<Doc> articles;
    private Context context;

    private final int IMAGE = 0, TITLE = 1;


    public ArticlesAdapter(Context context, List<Doc> articles) {
        this.articles = articles;
        this.context = context;
    }

    private Context getContext() {
        return context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        RecyclerView.ViewHolder viewHolder;

        switch (viewType){
            case TITLE:
                // Inflate the custom layout
                View titleView = inflater.inflate(R.layout.item_article_result_title, parent, false);
                // Return a new holder instance
                viewHolder = new TitleViewHolder(titleView);
                break;
            default:
                // Inflate the custom layout
                View imageView = inflater.inflate(R.layout.item_article_result, parent, false);
                // Return a new holder instance
                viewHolder = new ImageViewHolder(imageView);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Doc article = articles.get(position);

        switch (holder.getItemViewType()){
            case TITLE:
                TitleViewHolder tHolder = (TitleViewHolder) holder;
                tHolder.bind(article);
                break;
            default:
                ImageViewHolder iHolder = (ImageViewHolder) holder;
                iHolder.bind(article);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    @Override
    public int getItemViewType(int position) {
        Doc article = articles.get(position);

        return article.getMultimedia().size() > 0 ? IMAGE : TITLE;
    }



}
