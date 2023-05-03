package com.anhnhy.printerest.model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private String name;
    private String email;
    private String mKey;

    public User() {
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getKey() {
        return mKey;
    }

    @Exclude
    public void setKey(String key) {
        mKey = key;
    }
}
