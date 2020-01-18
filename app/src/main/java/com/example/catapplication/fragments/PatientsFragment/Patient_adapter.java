package com.example.catapplication.fragments.PatientsFragment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catapplication.R;
import com.example.catapplication.models.PatientRepData;

import java.util.ArrayList;
import java.util.List;


public class Patient_adapter extends RecyclerView.Adapter<Patient_adapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    private List<PatientRepData> items;

    private Context mContext;

    public Patient_adapter(Context context, ArrayList<PatientRepData> items) {

        this.mContext = context;
        this.items = items;
    }

    @NonNull
    @Override
    public Patient_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_patient, parent, false);
        Patient_adapter.ViewHolder holder = new Patient_adapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Patient_adapter.ViewHolder holder, final int position) {
        //to log which item is failed
        Log.d(TAG, "onBindViewHolder: called.");

        holder.name.setText(items.get(position).getDoctor_name());
        holder.dose.setText(items.get(position).getProduct_name());
        holder.category.setText(items.get(position).getCategory_name());
        holder.hospital.setText(items.get(position).getHospital_name());
        holder.parent_layout.setOnClickListener(view -> {

        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, dose, category, hospital, date;
        RelativeLayout parent_layout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            dose = itemView.findViewById(R.id.dose);
            category = itemView.findViewById(R.id.category);
            hospital = itemView.findViewById(R.id.hospital);
            parent_layout = itemView.findViewById(R.id.parent_layout);
        }
    }
}