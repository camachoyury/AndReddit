package com.camachoyury.andreddit.network;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by yury on 8/22/17.
 */

public interface FeedAPI {


    @GET("/top/.json")
    Observable<FeedResponse> getFeeds(@Query("limit") String limit , @Query("after") String after);

}
