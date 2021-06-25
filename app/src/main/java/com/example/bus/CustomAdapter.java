package com.example.bus;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{
    ArrayList<CustomRowItem> busList;
    Context context;

    public CustomAdapter(Context context, ArrayList<CustomRowItem> busList ) {
        this.context = context;
        this.busList = busList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_row, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CustomRowItem customRowItem = busList.get(position);
        holder.busname.setText(customRowItem.getBusName());
        holder.start.setText(customRowItem.getStartingPoint());
        holder.end.setText(customRowItem.getEndingPoint());
        holder.time.setText(customRowItem.getTime());
        holder.fare.setText(customRowItem.getFare());
        holder.type.setText(customRowItem.getType());
    }

    @Override
    public int getItemCount() {
        return busList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView busname, start, end, time, fare, type;

        public MyViewHolder(@NonNull  View itemView) {
            super(itemView);
            busname = itemView.findViewById(R.id.bus_name);
            start = itemView.findViewById(R.id.starting_point);
            end = itemView.findViewById(R.id.ending_point);
            time = itemView.findViewById(R.id.time);
            fare = itemView.findViewById(R.id.fare);
            type = itemView.findViewById(R.id.type);
        }
    }
}
