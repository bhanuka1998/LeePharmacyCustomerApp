package com.example.leepharmacycustomerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {
    private EditText txtSearch, txtDrugName, txtBrand;
    private Button btnSearch;
    private int drugID;
    private final String TAG = "RegisterActivity";
    private final String BaseURL = "http://192.168.1.4:8080";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        txtSearch = findViewById(R.id.searchDrug_txtSearch);
        txtDrugName = findViewById(R.id.searchDrug_txtDrugName);
        txtBrand = findViewById(R.id.searchDrug_txtBrandName);
        btnSearch = findViewById(R.id.searchDrug_btnSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drugID = Integer.parseInt(txtSearch.getText().toString());
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BaseURL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                JSONController jsonController = retrofit.create(JSONController.class);
                Call<Drugs> call = jsonController.getDrugs(drugID);
                call.enqueue(new Callback<Drugs>() {
                    @Override
                    public void onResponse(Call<Drugs> call, Response<Drugs> response) {
                        if(!response.isSuccessful()){
                            Toast.makeText(SearchActivity.this, "Invalid ID: " +response.code(), Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Response Error " +response.code());
                            return;
                        }
                        Drugs drugs = response.body();

                        txtDrugName.setText(drugs.getDrugName());
                        txtBrand.setText(drugs.getBrand().getBrandName());
                    }

                    @Override
                    public void onFailure(Call<Drugs> call, Throwable t) {
                        Toast.makeText(SearchActivity.this, "Error please try again.. " +t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Error: " +t.getMessage());
                    }
                });
            }
        });

    }
}