package com.example.leepharmacycustomerapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProfileFragment extends Fragment {

    private static final String TAG = "Profile";
    private static final String BaseURL = "http://192.168.1.4:8080";
    private String email;

    private Button btnUpdate;
    private EditText txtName, txtEmail, txtPhone;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public ProfileFragment(String email) {
        this.email = email;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        btnUpdate = view.findViewById(R.id.porBtnEdit);
        txtName = view.findViewById(R.id.proName);
        txtEmail = view.findViewById(R.id.proEmail);
        txtPhone = view.findViewById(R.id.proPhone);

        String email = this.email;

        Retrofit retrofit = new Retrofit.Builder() .baseUrl(BaseURL) .addConverterFactory(GsonConverterFactory.create()) .build();
        JSONController jsonController = retrofit.create(JSONController.class);
        Call<Customer> call = jsonController.getCustomerByEmail(email);

        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getContext(), "Invalid Details: " +response.code(), Toast.LENGTH_SHORT).show();
                }

                Customer customer = response.body();
                txtName.setText(customer.getCusName());
                txtEmail.setText(customer.getCusEmail());
                txtPhone.setText(customer.getCusPhone());

            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {

            }
        });

        return view;
    }
}