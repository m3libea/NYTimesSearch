package com.m3libea.nytimessearch;

import com.m3libea.nytimessearch.models.NYTimesResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by m3libea on 3/23/17.
 */

public interface NYTimesEndpoint {

    @GET("/svc/search/v2/articlesearch.json")
    rx.Observable<NYTimesResponse> articleSearch(
            @Query("page") int page,
            @Query("q") String query,
            @Query("begin_date") String beginDate,
            @Query("sort") String sort,
            @Query("fq") String filterQuery,
            @Query("api-key") String apiKey);

}
