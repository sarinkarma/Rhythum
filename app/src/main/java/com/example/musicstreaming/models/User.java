package com.example.musicstreaming.models;

public class User {
    private String _id;
    private String username;
    private String password;
    private String gender;
    private String email;
    private String dob;
    private String image;
    private String account;

    public User(String _id, String username, String password, String gender, String email, String dob, String image, String account) {
        this._id = _id;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.email = email;
        this.dob = dob;
        this.image = image;
        this.account = account;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
