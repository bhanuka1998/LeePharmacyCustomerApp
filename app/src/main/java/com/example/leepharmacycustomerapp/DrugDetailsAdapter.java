package com.example.leepharmacycustomerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DrugDetailsAdapter extends RecyclerView.Adapter<DrugDetailsAdapter.DrugsViewHolder> {
    List<DrugInfo> drugInfoList;
    Context context;

    public DrugDetailsAdapter(List<DrugInfo> drugInfoList, Context context) {
        this.drugInfoList = drugInfoList;
        this.context = context;
    }

    @NonNull
    @Override
    public DrugsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.drug_details,parent,false);
        return new DrugsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrugsViewHolder holder, int position) {
        DrugInfo drugInfo = drugInfoList.get(position);
        holder.drugName.setText(drugInfo.getDrugName());
        holder.brand.setText(drugInfo.getBrandName());
        holder.drugQty.setText((drugInfo.getFullQty().toString()));
    }

    @Override
    public int getItemCount() {
        return drugInfoList.size();
    }

    public class DrugsViewHolder extends  RecyclerView.ViewHolder{

        TextView drugName, brand, drugQty;

        public DrugsViewHolder(@NonNull View itemView) {
            super(itemView);

            drugName = itemView.findViewById(R.id.drug_details_drugName);
            brand = itemView.findViewById(R.id.drug_details_drugBrand);
            //drugQty = itemView.findViewById(R.id.drug_details_drugQty);

        }
    }

}
