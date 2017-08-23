package com.camachoyury.andreddit.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yury on 8/22/17.
 */

public class Child {
    @SerializedName("kind")
    public String kind;

    @SerializedName("data")
    public Feed data;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Feed getData() {
        return data;
    }

    public void setData(Feed data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Child{" +
                "kind='" + kind + '\'' +
                ", data=" + data.toString() +
                '}';
    }
}
