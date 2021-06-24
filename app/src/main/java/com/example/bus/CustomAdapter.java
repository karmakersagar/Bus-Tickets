package com.example.bus;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.myViewHolder>{
    private Context context;
    private List<CustomRowItem> list;

    public CustomAdapter(Context context, List<CustomRowItem> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_row,parent,false);
        myViewHolder myViewHolder = new myViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        Picasso.get().load(list.get(position).getImageResource()).placeholder(R.drawable.bus).into(holder.image);
        holder.journeyDate.setText(list.get(position).getJourneyDate().toString());
        holder.busNumber.setText(list.get(position).getBusNumber().toString());
        holder.busName.setText(list.get(position).getBusName().toString());
        holder.from.setText(list.get(position).getFrom().toString());
        holder.to.setText(list.get(position).getTo().toString());
        holder.busCondition.setText(list.get(position).getBusCondition().toString());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CircleImageView image;
        private TextView busName,busNumber,busCondition,from,to,journeyDate;

        public myViewHolder(View itemView) {
            super(itemView);
            image = (CircleImageView)itemView.findViewById(R.id.custom_imageViewId);
            busName = (TextView)itemView.findViewById(R.id.busNameId);
            busNumber = (TextView)itemView.findViewById(R.id.busNumberId);
            busCondition = (TextView)itemView.findViewById(R.id.busConditionId);
            from = (TextView)itemView.findViewById(R.id.fromId);
            to  = (TextView)itemView.findViewById(R.id.toId);
            journeyDate = (TextView)itemView.findViewById(R.id.busJourneyDateId);
            itemView.setOnClickListener(this);

        }
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            String BusName = list.get(position).getBusName();
            String JourneyDate = list.get(position).getJourneyDate();
            String BusCondition  = list.get(position).getBusCondition();

            Intent intent = new Intent(context,SeatChoose.class);

            intent.putExtra("busName",BusName);
            intent.putExtra("journeyDate",JourneyDate);
            intent.putExtra("busCondition",BusCondition);
            context.startActivity(intent);
        }
    }
}