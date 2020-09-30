package com.example.musicstreaming.models.request;

public class SignUpRequest {

    private String email;
    private String password;
    private String username;
    private String gender;
    private String dob;
    private String account;

    public SignUpRequest(String email, String password, String username, String gender, String dob, String account) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.gender = gender;
        this.dob = dob;
        this.account = account;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}

