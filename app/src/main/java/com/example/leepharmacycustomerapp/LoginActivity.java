package com.example.leepharmacycustomerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import at.favre.lib.crypto.bcrypt.BCrypt;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private EditText txtEmail;
    private EditText txtPassword;
    private Button btnLogin;
    private TextView tvRegister;
    private final String TAG = "LoginActivity";
    private final String BaseURL = "http://192.168.1.5:8080";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtEmail = findViewById(R.id.txtLoginEmail);
        txtPassword = findViewById(R.id.txtLoginPassword);
        tvRegister = findViewById(R.id.tvRegister);
        btnLogin = findViewById(R.id.btnLogin);

        Retrofit retrofit = new Retrofit.Builder() .baseUrl(BaseURL) .addConverterFactory(GsonConverterFactory.create()).build();
        JSONController jsonController = retrofit.create(JSONController.class);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cusEmail, cusPassword;
                cusEmail = txtEmail.getText().toString();
                cusPassword = txtPassword.getText().toString();

                if(cusEmail.equals("") || cusPassword.equals("")){
                    Toast.makeText(LoginActivity.this, "Please fill out required fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                Call<CustomerLogin> call = jsonController.checkCustLoginInfo(cusEmail);
                call.enqueue(new Callback<CustomerLogin>() {
                    @Override
                    public void onResponse(Call<CustomerLogin> call, Response<CustomerLogin> response) {
                        if(!response.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Invalid Email or Password... Please try again!!!", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Invalid details " +response.code());
                        }


                        CustomerLogin customerLogin = response.body();
                        //Toast.makeText(LoginActivity.this, customerLogin.getCusPassword(), Toast.LENGTH_LONG).show();
                        BCrypt.Result result = BCrypt.verifyer().verify(cusPassword.toCharArray(), customerLogin.getCusPassword());
                        if(result.verified == false){
                            Toast.makeText(LoginActivity.this, "Invalid Email or Password... Please try again!!!", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Invalid details " +response.code());
                        }
                        else if(customerLogin.getStatus()!=1){
                            Toast.makeText(LoginActivity.this, "Your account has been disabled....", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Invalid details " +response.code());
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Welcome back", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }
                    }

                    @Override
                    public void onFailure(Call<CustomerLogin> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Login Error...", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, t.getMessage());
                    }
                });
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegiserActivity.class));
            }
        });

    }
}