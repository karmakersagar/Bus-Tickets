package com.example.bus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

public class TicketDetailsActivity extends AppCompatActivity {

    private TextView passengerNameTV, mobileTV, emailTV, busNameTV, fromTV, toTV, journeyDateTV, busConditionTV, seatTV, timeTV, costTV, ticketIDTV;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase db;
    private DatabaseReference root, databaseReference;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_details);
        Intent intent = getIntent();
        String ticketID = intent.getStringExtra("ticketID").toString();
        String issueTime = intent.getStringExtra("issueTime").toString();



        passengerNameTV  = findViewById(R.id.passengerNameTicket);
        mobileTV = findViewById(R.id.mobileNumberTicket);
        emailTV = findViewById(R.id.emailTicket);
        busNameTV = findViewById(R.id.busNameTicket);
        fromTV = findViewById(R.id.fromTicket);
        toTV = findViewById(R.id.toTicket);
        journeyDateTV = findViewById(R.id.journeyDateTicket);
        busConditionTV = findViewById(R.id.busConditionTicket);
        seatTV = findViewById(R.id.seatTicket);
        timeTV = findViewById(R.id.timeTicket);
        costTV = findViewById(R.id.totalCostTicket);
        ticketIDTV = findViewById(R.id.ticketIDTicket);


        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance("https://buss-886c2-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users").child(userID);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Users usr = snapshot.getValue(Users.class);
                passengerNameTV.setText(usr.getFirstName() + " " + usr.getLastName());
                mobileTV.setText(usr.getPhoneNo());
                emailTV.setText(usr.getEmailId());
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        db = FirebaseDatabase.getInstance("https://buss-886c2-default-rtdb.asia-southeast1.firebasedatabase.app/");
        root = db.getReference("users").child(userID).child("Tickets").child(ticketID);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                TicketDetailsInformation ticketDetailsInformation = snapshot.getValue(TicketDetailsInformation.class);
                busNameTV.setText(ticketDetailsInformation.getBusName());
                fromTV.setText(ticketDetailsInformation.getFrom());
                toTV.setText(ticketDetailsInformation.getTo());
                journeyDateTV.setText(ticketDetailsInformation.getJourneyDate());
                busConditionTV.setText(ticketDetailsInformation.getBusCondition());
                seatTV.setText(ticketDetailsInformation.getSeats());
                timeTV.setText(ticketDetailsInformation.getTime());
                costTV.setText(ticketDetailsInformation.getTotalCost());
                ticketIDTV.setText(ticketDetailsInformation.getTicketID());
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}