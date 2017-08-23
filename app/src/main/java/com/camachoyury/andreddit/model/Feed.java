package com.camachoyury.andreddit.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yury on 8/22/17.
 */

public class Feed {


    @SerializedName("thumbnail_width")
    public Object thumbnailWidth;

    @SerializedName("likes")
    public Object likes;

    @SerializedName("id")
    public String id;

    @SerializedName("title")
    public String title;

    @SerializedName("thumbnail")
    public String thumbnail;

    @SerializedName("thumbnail_height")
    public Object thumbnailHeight;

    @SerializedName("created")
    public long created;

    @SerializedName("author")
    public String author;

    @SerializedName("url")
    public String url;
    @SerializedName("created_utc")
    public long createdUtc;

    @SerializedName("num_comments")
    public int numComments;

    @SerializedName("permalink")
    public String permalink;


    public Object getThumbnailWidth() {
        return thumbnailWidth;
    }

    public void setThumbnailWidth(Object thumbnailWidth) {
        this.thumbnailWidth = thumbnailWidth;
    }

    public Object getLikes() {
        return likes;
    }

    public void setLikes(Object likes) {
        this.likes = likes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Object getThumbnailHeight() {
        return thumbnailHeight;
    }

    public void setThumbnailHeight(Object thumbnailHeight) {
        this.thumbnailHeight = thumbnailHeight;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getCreatedUtc() {
        return createdUtc;
    }

    public void setCreatedUtc(long createdUtc) {
        this.createdUtc = createdUtc;
    }

    public int getNumComments() {
        return numComments;
    }

    public void setNumComments(int numComments) {
        this.numComments = numComments;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    @Override
    public String toString() {
        return "Feed{" +
                "thumbnailWidth=" + thumbnailWidth +
                ", likes=" + likes +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", thumbnailHeight=" + thumbnailHeight +
                ", created=" + created +
                ", author='" + author + '\'' +
                ", url='" + url + '\'' +
                ", createdUtc=" + createdUtc +
                ", numComments=" + numComments +
                ", permalink=" + permalink +
                '}';
    }
}
