package com.example.leepharmacycustomerapp;

import android.app.Presentation;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JSONController {

    @POST("/saveCustomer")
    Call<ResponseBody> saveUser (@Body Customer customer);

    @GET("/checkCustomerLogins/{email}")
    Call<CustomerLogin> checkCustLoginInfo(@Path("email") String email);

    @GET("/getDrugById/{id}")
    Call<Drugs> getDrugs(@Path("id") Integer drugID);

    @GET("/getAllDrugsByNames")
    Call<List<DrugInfo>> getDrugDetails();

    @GET("/getCustomerByEmail/{email}")
    Call<Customer> getCustomerByEmail(@Path("email") String email);

    @POST("/addPrescription")
    Call<ResponseBody> addPrescription(@Body Prescription prescription);

}
