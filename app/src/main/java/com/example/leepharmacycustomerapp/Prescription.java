package com.example.leepharmacycustomerapp;

public class Prescription {

    private Integer presID;

    private  String cusName;

    private String docName;

    private String presContent;

    public Prescription(String cusName, String docName, String presContent) {
        this.cusName = cusName;
        this.docName = docName;
        this.presContent = presContent;
    }

    public Integer getPresID() {
        return presID;
    }

    public void setPresID(Integer presID) {
        this.presID = presID;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getPresContent() {
        return presContent;
    }

    public void setPresContent(String presContent) {
        this.presContent = presContent;
    }
}
