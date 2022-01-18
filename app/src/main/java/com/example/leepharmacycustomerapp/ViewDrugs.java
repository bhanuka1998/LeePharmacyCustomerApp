package com.example.leepharmacycustomerapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewDrugs extends Fragment {
    private static final String TAG = "ViewDrugs";
    private static final String BaseURL = "http://192.168.1.5:8080";

    private DrugDetailsAdapter drugDetailsAdapter;
    private ArrayList<DrugInfo> drugsArrayList = new ArrayList<>();
    private RecyclerView recyclerView;

    public ViewDrugs(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_view_drugs, container, false);

        recyclerView = view.findViewById(R.id.rv_drugDetails);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        setHasOptionsMenu(true);
        Retrofit retrofit = new Retrofit.Builder() .baseUrl(BaseURL) .addConverterFactory(GsonConverterFactory.create()) .build();

        JSONController jsonController = retrofit.create(JSONController.class);
        Call<List<DrugInfo>> call = jsonController.getDrugDetails();

        call.enqueue(new Callback<List<DrugInfo>>() {
            @Override
            public void onResponse(Call<List<DrugInfo>> call, Response<List<DrugInfo>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), "Error"+response.code(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG,"API Response error :"+ response.code());
                    return;
                }
                else {
                    List<DrugInfo> drugList = response.body();
                    drugDetailsAdapter = new DrugDetailsAdapter(drugList,getContext());
                    recyclerView.setAdapter(drugDetailsAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<DrugInfo>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}