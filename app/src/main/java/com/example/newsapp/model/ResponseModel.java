package com.example.newsapp.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;
public class ResponseModel {
    @SerializedName("status")
    private String status;
    @SerializedName("totalResults")
    private int totalResults;
    @SerializedName("articles")
    private List<Article> articles = null;

    public ResponseModel(String status, int totalResults, List<Article> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }
    public int getTotalResults() {
        return totalResults;
    }
    public List<Article> getArticles() {
        return articles;
    }

}
