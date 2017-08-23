package com.camachoyury.andreddit.network;

import io.reactivex.Observable;

/**
 * Created by yury on 8/22/17.
 */

public class FeedsRepository {

    public Observable<FeedResponse> getFeeds(String limit, String after) {

        FeedAPI api = RestClient.getInstance().createService(FeedAPI.class);
        return api.getFeeds(limit,after);
    }
}
