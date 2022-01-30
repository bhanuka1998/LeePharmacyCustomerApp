package com.example.leepharmacycustomerapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PrescriptionFragment extends Fragment {

    private static final String TAG = "Prescription";
    private static final String BaseURL = "http://192.168.1.4:8080";

    private EditText txtCusName, txtDocName, txtPresContent;
    private Button btnAdd;


    public PrescriptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_prescription, container, false);
        btnAdd = view.findViewById(R.id.btnPresAdd);
        txtCusName = view.findViewById(R.id.txtCusName);
        txtDocName = view.findViewById(R.id.txtDocName);
        txtPresContent = view.findViewById(R.id.txtPresContent);

        Retrofit retrofit = new Retrofit.Builder() .baseUrl(BaseURL) .addConverterFactory(GsonConverterFactory.create()) .build();
        JSONController jsonController = retrofit.create(JSONController.class);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cusName, docName, presContent;
                cusName = txtCusName.getText().toString();
                docName = txtDocName.getText().toString();
                presContent = txtPresContent.getText().toString();

                if(cusName.equals("") || docName.equals("") || presContent.equals("")){
                    Toast.makeText(getContext(), "Please fill required fields", Toast.LENGTH_SHORT).show();
                }

                Prescription prescription = new Prescription(cusName, docName, presContent);
                Call<ResponseBody> call = jsonController.addPrescription(prescription);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(!response.isSuccessful()){
                            Toast.makeText(getContext(), "Failed to upload: "+response.code(), Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Response Error: "+response.code());
                            return;
                        }
                        Toast.makeText(getContext(), "You have uploaded the prescription", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getContext(), MainActivity.class));
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getContext(), "Unable to upload: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG, t.getMessage());
                    }
                });
            }
        });

        return view;
    }
}