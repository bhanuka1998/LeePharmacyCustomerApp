package com.example.leepharmacycustomerapp;

public class CustomerLogin {
    private Integer cusLogID;
    private  String cusEmail;
    private String cusPassword;
    private int status;

    public CustomerLogin() {
    }

    public CustomerLogin(String cusEmail, String cusPassword) {
        this.cusEmail = cusEmail;
        this.cusPassword = cusPassword;
        this.status = 1;
    }

    public Integer getCusLogID() {
        return cusLogID;
    }

    public void setCusLogID(Integer cusLogID) {
        this.cusLogID = cusLogID;
    }

    public String getCusEmail() {
        return cusEmail;
    }

    public void setCusEmail(String cusEmail) {
        this.cusEmail = cusEmail;
    }

    public String getCusPassword() {
        return cusPassword;
    }

    public void setCusPassword(String cusPassword) {
        this.cusPassword = cusPassword;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
