package com.example.bus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TicketActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String ticketID, busName, fromLocation, toLocation, issueDate, issueTime, userID;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase db;
    private DatabaseReference root;
    private TicketActivityAdapter adapter;
    private ArrayList<TicketActivityItem> list;
    private ValueEventListener valueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        recyclerView = findViewById(R.id.ticketRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<TicketActivityItem>();
        adapter = new TicketActivityAdapter(this, list);
        recyclerView.setAdapter(adapter);

        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();
        db = FirebaseDatabase.getInstance("https://buss-886c2-default-rtdb.asia-southeast1.firebasedatabase.app/");
        root = db.getReference("users").child(userID).child("Tickets");

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        ticketID = dataSnapshot.child("ticketID").getValue(String.class);
                        busName = dataSnapshot.child("busName").getValue(String.class);
                        fromLocation = dataSnapshot.child("from").getValue(String.class);
                        toLocation = dataSnapshot.child("to").getValue(String.class);
                        issueDate = dataSnapshot.child("issueDate").getValue(String.class);
                        issueTime = dataSnapshot.child("issueTime").getValue(String.class);
                        list.add(new TicketActivityItem(ticketID,busName,fromLocation,toLocation,issueDate,issueTime));
                        Log.d("Tag", "Data" + ticketID );
                    }
                    adapter.notifyDataSetChanged();
                    Toast.makeText(TicketActivity.this, "Here are your tickets!", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(TicketActivity.this, "You Don't Have Any Ticket Yet!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}