package com.m3libea.nytimessearch.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.m3libea.nytimessearch.R;
import com.m3libea.nytimessearch.models.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by m3libea on 3/13/17.
 */

public class ArticleArrayAdapter extends ArrayAdapter<Article> {

    @BindView(R.id.ivimage) ImageView ivImage;
    @BindView(R.id.tvTitle) TextView tvTitle;


    public ArticleArrayAdapter(Context context, List<Article> articles){
        super(context, android.R.layout.simple_list_item_1, articles);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get data item for position
        Article article = this.getItem(position);
        //check if reused
        //not using recycled view -> Inflate the layout
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_article_result, parent, false);
        }

        ButterKnife.bind(this, convertView);

        //Clear out recycled image from converView
        ivImage.setImageResource(0);

        tvTitle.setText(article.getHeadline());

        //populate thumbnail image

        String thumbnail = article.getThumbNail();

        if(!TextUtils.isEmpty(thumbnail)){
            Picasso.with(getContext()).load(thumbnail).into(ivImage);
        }

        return convertView;
    }


}

