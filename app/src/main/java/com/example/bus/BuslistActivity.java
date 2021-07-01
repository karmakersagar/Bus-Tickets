package com.example.bus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BuslistActivity extends AppCompatActivity {
    //private static final String TAG = BuslistActivity;
    private String busName, start, end, time, fare, type,fromCity,toCity,jdate, busID;
    private RecyclerView recyclerView;

   // Bundle busListIntent = getIntent().getExtras();

//    passSearchData searchData = new passSearchData(fromCity,toCity,date);
//    final String fromSearchCity = searchData.getFromCity();
//    final String toSearchCity = searchData.getToCity();
//    final String journeyDate = searchData.getDate();


    private FirebaseDatabase db;
    private DatabaseReference root;
    private CustomAdapter adapter;
    private ArrayList<CustomRowItem> list;
    private ValueEventListener valueEventListener;
    Calendar calendar;
    String currentDate, currentTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buslist);

        Intent intent = getIntent();
        String FromLocation = intent.getStringExtra("fromLocation").toString();
        String ToLocation = intent.getStringExtra("toLocation").toString();
        String JourneyDate = intent.getStringExtra("journeyDate").toString();
        calendar = Calendar.getInstance();
        SimpleDateFormat currentDateFormat = new SimpleDateFormat("d - M - yyyy");
        currentDate = currentDateFormat.format(calendar.getTime());
//        System.out.println(currentDate);
        SimpleDateFormat currentTimeFormat = new SimpleDateFormat("HH:mm");
        currentTime = currentTimeFormat.format(calendar.getTime());
//        System.out.println(currentTime);
//        System.out.println(currentDate);
//        System.out.println(JourneyDate);


        db = FirebaseDatabase.getInstance("https://buss-886c2-default-rtdb.asia-southeast1.firebasedatabase.app/");
        root = db.getReference("BusDetails").child(FromLocation).child(ToLocation);

//        Intent intentDatePass = new Intent(getApplicationContext(),SeatChoose.class);
//        intentDatePass.putExtra("journeyDate" , JourneyDate);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<CustomRowItem>();
        adapter = new CustomAdapter(this, list);
        recyclerView.setAdapter(adapter);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if(currentDate.equals(JourneyDate)){
                            time = dataSnapshot.child("Time").getValue(String.class);
                            if(currentTime.compareTo(time)==0 || currentTime.compareTo(time)<0){
                                busName = dataSnapshot.child("Bus Name").getValue(String.class);
                                start = dataSnapshot.child("Starting Point").getValue(String.class);
                                end = dataSnapshot.child("Ending Point").getValue(String.class);
                               // time = dataSnapshot.child("Time").getValue(String.class);
                                fare = dataSnapshot.child("Fare").getValue(String.class);
                                type = dataSnapshot.child("Type").getValue(String.class);
                                busID = dataSnapshot.child("ID").getValue(String.class);
                                list.add(new CustomRowItem(busName, start, end, time, fare, type, busID, JourneyDate));
                            }

                        }

                        else{
                            busName = dataSnapshot.child("Bus Name").getValue(String.class);
                            start = dataSnapshot.child("Starting Point").getValue(String.class);
                            end = dataSnapshot.child("Ending Point").getValue(String.class);
                            time = dataSnapshot.child("Time").getValue(String.class);
                            fare = dataSnapshot.child("Fare").getValue(String.class);
                            type = dataSnapshot.child("Type").getValue(String.class);
                            busID = dataSnapshot.child("ID").getValue(String.class);
                            list.add(new CustomRowItem(busName, start, end, time, fare, type, busID, JourneyDate));
                        }



//                    CustomRowItem model = dataSnapshot.getValue(CustomRowItem.class);
//                    list.add(model);
                    }
                    adapter.notifyDataSetChanged();
                }

                else{
                    Toast.makeText(BuslistActivity.this, "Sorry!! No buses available", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}