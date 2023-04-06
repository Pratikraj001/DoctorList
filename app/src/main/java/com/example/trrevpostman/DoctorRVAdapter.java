package com.example.trrevpostman;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DoctorRVAdapter extends RecyclerView.Adapter<DoctorRVAdapter.ViewHolder> {

    private List<DataModal> list;
    private Context context;
    LayoutInflater inflater;


    public DoctorRVAdapter(Context applicationContext, List<DataModal> list) {
        this.list = list;
        this.inflater = LayoutInflater.from(applicationContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(context).inflate(R.layout.doctor_list,parent,false);
        return new ViewHolder(inflater.inflate(R.layout.doctor_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataModal dataModal = list.get(position);

        holder.docID.setText(dataModal.getId());
        holder.nameTV.setText(dataModal.getName());
        holder.emailTV.setText(dataModal.getEmail());
        holder.genderTV.setText(dataModal.getGender());
        holder.monthTV.setText(dataModal.getMonth());
        holder.yearTV.setText(dataModal.getYear());

    }

    @Override
    public int getItemCount() {
        if(list==null) return 0;
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView docID, nameTV, emailTV, genderTV, monthTV, yearTV;

        private CardView cardView;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            docID = itemView.findViewById(R.id.DoctorIdTV);
            nameTV = itemView.findViewById(R.id.DoctorNameTV);
            emailTV = itemView.findViewById(R.id.DoctorEmailTV);
            genderTV = itemView.findViewById(R.id.DoctorGenderTV);
            monthTV = itemView.findViewById(R.id.DoctorMonthTV);
            yearTV = itemView.findViewById(R.id.DoctorYearTV);
            cardView = itemView.findViewById(R.id.cardview);
        }
    }



}


