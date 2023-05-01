package com.anhnhy.printerest.model;

import java.io.Serializable;

public class Favorite implements Serializable {
    private int id;
    private String user_id;
    private String image_id;

    public Favorite() {
    }

    public Favorite(String user_id, String image_id) {
        this.user_id = user_id;
        this.image_id = image_id;
    }

    public Favorite(int id, String user_id, String image_id) {
        this.id = id;
        this.user_id = user_id;
        this.image_id = image_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }
}
