package com.example.newsapp.model;

import com.google.gson.annotations.SerializedName;
public class SourceModel {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;

    public SourceModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
