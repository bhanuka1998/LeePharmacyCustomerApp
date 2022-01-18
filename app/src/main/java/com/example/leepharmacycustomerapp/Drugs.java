package com.example.leepharmacycustomerapp;

public class Drugs {
    private Integer drugID;
    private String drugName;
    private Integer drugQty;
    private Brand brand;

    public Drugs() {
    }

    public Drugs(String drugName, Integer drugQty, Brand brand) {
        this.drugName = drugName;
        this.drugQty = drugQty;
        this.brand = brand;
    }

    public Integer getDrugID() {
        return drugID;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public Integer getDrugQty() {
        return drugQty;
    }

    public void setDrugQty(Integer drugQty) {
        this.drugQty = drugQty;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}
