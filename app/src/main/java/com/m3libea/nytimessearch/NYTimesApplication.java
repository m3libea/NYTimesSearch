package com.m3libea.nytimessearch;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * Created by m3libea on 3/23/17.
 */

public class NYTimesApplication extends Application {

    private Retrofit retrofit;

    public Retrofit getRetrofit() {
        return retrofit;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        RxJavaCallAdapterFactory rxNYAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

         retrofit = new Retrofit.Builder()
                .baseUrl(Config.APIBASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(rxNYAdapter)
                .build();
    }
}
