package com.example.newsapp.model;

import com.google.gson.annotations.SerializedName;
public class Article{
    @SerializedName("source")
    private SourceModel source;
    @SerializedName("author")
    private String author;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("url")
    private String url;
    @SerializedName("urlToImage")
    private String urlToImage;
    @SerializedName("publishedAt")
    private String publishedAt;
    @SerializedName("content")
    private String content;

    public Article(SourceModel source,
                   String author, String title,
                   String description, String url,
                   String urlToImage, String publishedAt) {
        this.source = source;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
    }



    public SourceModel getSource() {
        return source;
    }

    public String getAuthor() {
        return author;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getUrlToImage() {
        return urlToImage;
    }
    public String getPublishedAt() {
        return publishedAt;
    }
    public String getUrl() {
        return url;
    }
    public String getContent() {
        return content;
    }

}
