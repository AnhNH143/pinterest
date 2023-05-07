package com.anhnhy.printerest.model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Image implements Serializable {
    private String name;
    private String imageUrl;
    private String key;
    private String senderId;
    private HashMap<String, String> likeIds;

    public Image() {
    }

    public Image(String name, String imageUrl, String senderId) {
        if (name.trim().equals("")) {
            name = "no name";
        }
        this.name = name;
        this.imageUrl = imageUrl;
        this.senderId = senderId;
        this.likeIds = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public HashMap<String,String> getLikeIds() {
        return likeIds;
    }

    public void setLikeIds(HashMap<String, String> likeIds) {
        this.likeIds = likeIds;
    }

    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }
}
