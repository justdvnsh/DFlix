package com.divyansh.dflix.models;

public class User {

    private String uid;
    private String email;
    private boolean isAuthenticated;

    public User(String uid, String email) {
        this.uid = uid;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getUid() {
        return uid;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
