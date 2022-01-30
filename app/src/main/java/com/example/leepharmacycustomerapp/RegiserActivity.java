package com.example.leepharmacycustomerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import at.favre.lib.crypto.bcrypt.BCrypt;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegiserActivity extends AppCompatActivity {
    private EditText txtName;
    private EditText txtEmail;
    private EditText txtPhone;
    private EditText txtPassword;
    private Button btnRegister;
    private final String TAG = "RegisterActivity";
    private final String BaseURL = "http://192.168.1.4:8080";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regiser);
        txtName = findViewById(R.id.txtCustomerName);
        txtEmail = findViewById(R.id.txtCustomerEmail);
        txtPhone = findViewById(R.id.txtCustomerPhone);
        txtPassword = findViewById(R.id.txtCustomerPassword);
        btnRegister = findViewById(R.id.btnCustomerRegister);

        Retrofit retrofit = new Retrofit.Builder() .baseUrl(BaseURL) .addConverterFactory(GsonConverterFactory.create()) .build();
        JSONController jsonController = retrofit.create(JSONController.class);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cusName, cusEmail, cusPhone, cusPassword;
                cusName = txtName.getText().toString();
                cusEmail = txtEmail.getText().toString();
                cusPhone = txtPhone.getText().toString();
                cusPassword = txtPassword.getText().toString();



                if(cusName.equals("") || cusEmail.equals("") || cusPhone.equals("") || cusPassword.equals("" )){
                    Toast.makeText(RegiserActivity.this, "Please fill required fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                String bCryptPass = BCrypt.withDefaults().hashToString(12, cusPassword.toCharArray());

                CustomerLogin customerLogin = new CustomerLogin(cusEmail, bCryptPass);
                Customer customer = new Customer(cusName, cusEmail, cusPhone, customerLogin);
                Call<ResponseBody> call = jsonController.saveUser(customer);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(!response.isSuccessful()){
                            Toast.makeText(RegiserActivity.this, "Registration failed: "+response.code(), Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Response Error: "+response.code());
                          //  Toast.makeText(RegiserActivity.this, customer.getCusEmail(), Toast.LENGTH_LONG).show();
                            return;
                        }
                        Toast.makeText(RegiserActivity.this, "You have been registered", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegiserActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(RegiserActivity.this, "Unable to register: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG, t.getMessage());
                    }
                });
            }
        });
    }


}