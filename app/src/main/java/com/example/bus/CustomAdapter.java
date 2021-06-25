package com.example.bus;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{
    private List<CustomRowItem> busList;
    private Context context;

    public CustomAdapter(Context context, List<CustomRowItem> busList ) {
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

    public  class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView busname, start, end, time, fare, type;

        public MyViewHolder(@NonNull  View itemView) {
            super(itemView);
            busname = itemView.findViewById(R.id.bus_name);
            start = itemView.findViewById(R.id.starting_point);
            end = itemView.findViewById(R.id.ending_point);
            time = itemView.findViewById(R.id.time);
            fare = itemView.findViewById(R.id.fare);
            type = itemView.findViewById(R.id.type);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            String busName = busList.get(position).getBusName();
            String startPoint = busList.get(position).getStartingPoint();
            String endPoint = busList.get(position).getEndingPoint();
            String Time = busList.get(position).getTime();
            String Fare = busList.get(position).getFare();
            String Type = busList.get(position).getType();

            Intent intent = new Intent(context, SeatChoose.class);

            intent.putExtra("busName", busName);
            intent.putExtra("start", startPoint);
            intent.putExtra("end",endPoint);
            intent.putExtra("time",Time);
            intent.putExtra("fare",Fare);
            intent.putExtra("type",Type);

            context.startActivity(intent);
//
//        }
    }


  }
}

