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

import java.util.List;

public class TicketActivityAdapter extends RecyclerView.Adapter<TicketActivityAdapter.TicketViewHolder> {

    private List<TicketActivityItem> ticketList;
    private Context context;

    public TicketActivityAdapter(Context context,List<TicketActivityItem> ticketList ) {
        this.context = context;
        this.ticketList = ticketList;

    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.ticketshown, parent, false);
        return new TicketViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {

        TicketActivityItem ticketActivityItem = ticketList.get(position);
        holder.ticketID.setText(ticketActivityItem.getTicketID());
        holder.busName.setText(ticketActivityItem.getBusName());
        holder.fromLocation.setText(ticketActivityItem.getFromLocation());
        holder.toLocation.setText(ticketActivityItem.getToLocation());
        holder.issueDate.setText(ticketActivityItem.getIssueDate());
        holder.issueTime.setText(ticketActivityItem.getIssueTime());

    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }


    public class TicketViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView ticketID, busName, fromLocation, toLocation, issueTime, issueDate;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            ticketID = itemView.findViewById(R.id.ticketID);
            busName = itemView.findViewById(R.id.busname);
            fromLocation = itemView.findViewById(R.id.fromLocation);
            toLocation = itemView.findViewById(R.id.toLocation);
            issueTime = itemView.findViewById(R.id.issueTime);
            issueDate = itemView.findViewById(R.id.issueDate);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Intent intent = new Intent(context,TicketDetailsActivity.class);
            context.startActivity(intent);
        }
    }
}
