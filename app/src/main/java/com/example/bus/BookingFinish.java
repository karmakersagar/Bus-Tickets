package com.example.bus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bus.fragments.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class BookingFinish extends AppCompatActivity {
    private Button homeButton;

    String busNam,fromCity,toCity,fare,seatsName,time,busCondition,issueDate,issueTime,journeyDate,userId,message;
    private TextView busNameTextView, journeyDateTextView, ticketIDTextView;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    Calendar calendar;
    Map<String,String> ticketDetailsMap = new HashMap<String, String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_finish);
       Intent intentTickets = getIntent();
        calendar = Calendar.getInstance();
        SimpleDateFormat currentDateFormat = new SimpleDateFormat("dd - M - yyyy");
        issueDate = currentDateFormat.format(calendar.getTime());
        SimpleDateFormat currentTimeFormat = new SimpleDateFormat("HH:mm");
        issueTime = currentTimeFormat.format(calendar.getTime());
         busNam = intentTickets.getStringExtra("busName").toString();
         fromCity = intentTickets.getStringExtra("from").toString();
         toCity = intentTickets.getStringExtra("to").toString();
         journeyDate = intentTickets.getStringExtra("journeyDate").toString();
         fare = intentTickets.getStringExtra("Fare").toString();
         time = intentTickets.getStringExtra("time").toString();
         busCondition = intentTickets.getStringExtra("busCondition").toString();
         seatsName = intentTickets.getStringExtra("seatName").toString();
         message = intentTickets.getStringExtra("noti");
         firebaseAuth = FirebaseAuth.getInstance();
         userId = firebaseAuth.getCurrentUser().getUid();

//        System.out.println(busNam);
//        System.out.println(fromCity);
//        System.out.println(toCity);
//        System.out.println(fare);
//        System.out.println(journeyDate);
//        System.out.println(time);
//        System.out.println(busCondition);
//        System.out.println(seatsName);
//        System.out.println(issueDate);
//        System.out.println(issueTime);
        ticketDetailsMap.put("busName",busNam);
        ticketDetailsMap.put("from",fromCity);
        ticketDetailsMap.put("to",toCity);
        ticketDetailsMap.put("journeyDate",journeyDate);
        ticketDetailsMap.put("time",time);
        ticketDetailsMap.put("busCondition",busCondition);
        ticketDetailsMap.put("TotalCost",fare);
        ticketDetailsMap.put("seats",seatsName);
        ticketDetailsMap.put("issueDate",issueDate);
        ticketDetailsMap.put("issueTime",issueTime);

        busNameTextView = findViewById(R.id.busName);
        journeyDateTextView = findViewById(R.id.busJourneyDateId);
        ticketIDTextView = findViewById(R.id.ticketID);

        databaseReference = FirebaseDatabase.getInstance("https://buss-886c2-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users").child(userId).child("Tickets");
        String key = databaseReference.push().getKey();
        ticketDetailsMap.put("ticketID",key);
        databaseReference.child(key).setValue(ticketDetailsMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
               // Toast.makeText(BookingFinish.this, "Tickets database Created ", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
               // Toast.makeText(BookingFinish.this, "Tickets Database creation failed ", Toast.LENGTH_SHORT).show();
            }
        });

        busNameTextView.setText(busNam);
        journeyDateTextView.setText(journeyDate);
        ticketIDTextView.setText(key);

        homeButton = findViewById(R.id.homeButtonId);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}