package com.djac21.fingerprintauth.Models;

import com.google.gson.annotations.SerializedName;

public class NewsModel {
    @SerializedName("publishedAt")
    private String date;

    @SerializedName("author")
    private String author;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("urlToImage")
    private String image;

    @SerializedName("url")
    private String url;

    public NewsModel(String author, String title, String description, String date, String image, String url) {
        this.author = author;
        this.title = title;
        this.date = date;
        this.description = description;
        this.image = image;
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
