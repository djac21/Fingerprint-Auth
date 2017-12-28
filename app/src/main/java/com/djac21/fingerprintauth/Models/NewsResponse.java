package com.djac21.fingerprintauth.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsResponse {
    @SerializedName("articles")
    private List<NewsModel> results;

    public List<NewsModel> getResults() {
        return results;
    }

    public void setResults(List<NewsModel> results) {
        this.results = results;
    }
}
