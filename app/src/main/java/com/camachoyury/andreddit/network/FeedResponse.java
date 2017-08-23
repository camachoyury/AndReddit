package com.camachoyury.andreddit.network;

import com.camachoyury.andreddit.model.Data;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yury on 8/22/17.
 */

public class FeedResponse {


    @SerializedName("kind")
    public String kind;

    @SerializedName("data")
    public Data data;

    @Override
    public String toString() {
        return "FeedResponse{" +
                "kind='" + kind + '\'' +
                ", data=" + data.toString() +"\n"+
                '}';
    }
}
