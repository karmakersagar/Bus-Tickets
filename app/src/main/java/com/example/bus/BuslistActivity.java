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

import java.util.ArrayList;
import java.util.List;

public class BuslistActivity extends AppCompatActivity {
    //private static final String TAG = BuslistActivity;
    private String busName, start, end, time, fare, type,fromCity,toCity,jdate;
    private RecyclerView recyclerView;

   // Bundle busListIntent = getIntent().getExtras();

//    passSearchData searchData = new passSearchData(fromCity,toCity,date);
//    final String fromSearchCity = searchData.getFromCity();
//    final String toSearchCity = searchData.getToCity();
//    final String journeyDate = searchData.getDate();

    private FirebaseDatabase db = FirebaseDatabase.getInstance("https://buss-886c2-default-rtdb.asia-southeast1.firebasedatabase.app/");
    private DatabaseReference root = db.getReference("BusDetails").child("Dhaka").child("Rajshahi");
    private CustomAdapter adapter;
    private ArrayList<CustomRowItem> list;
    private ValueEventListener valueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buslist);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<CustomRowItem>();
        adapter = new CustomAdapter(this, list);

        recyclerView.setAdapter(adapter);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    busName = dataSnapshot.child("Bus Name").getValue(String.class);
                    start = dataSnapshot.child("Starting Point").getValue(String.class);
                    end  = dataSnapshot.child("Ending Point").getValue(String.class);
                    time = dataSnapshot.child("Time").getValue(String.class);
                    fare  = dataSnapshot.child("Fare").getValue(String.class);
                    type = dataSnapshot.child("Type").getValue(String.class);

                    list.add(new CustomRowItem(busName, start, end, time, fare, type));
//                    CustomRowItem model = dataSnapshot.getValue(CustomRowItem.class);
//                    list.add(model);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
//    private RecyclerView recyclerView;
//    private DatabaseReference databaseReference;
//    CustomAdapter busAdapter;
//    ArrayList<CustomRowItem> busList;
//    private FirebaseAuth firebaseAuth;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_buslist);
//
////        String[] busesName = getResources().getStringArray(R.array.bus_name);
////        String[] busesNumber = getResources().getStringArray(R.array.bus_number);
////        String[] busesCondition = getResources().getStringArray(R.array.bus_codition);
////        String[] busesFrom = getResources().getStringArray(R.array.from);
////        String[] busesTo  = getResources().getStringArray(R.array.to);
////        String[] busesJourneyDate = getResources().getStringArray(R.array.journey_date);
//
//        recyclerView = (RecyclerView) findViewById(R.id.recyclerId);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        busList = new ArrayList<>();
//
//        busAdapter = new CustomAdapter(this, busList);
//        recyclerView.setAdapter(busAdapter);
//        firebaseAuth = FirebaseAuth.getInstance();
//        databaseReference = FirebaseDatabase.getInstance("https://buss-886c2-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("BusDetails").child("Dhaka").child("Rajshahi");
//        final String fromBus = getIntent().getStringExtra("fromLocation");
//        final String toBus = getIntent().getStringExtra("toLocation");
//        final String date = getIntent().getStringExtra("date");
//
//
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    CustomRowItem model = dataSnapshot.getValue(CustomRowItem.class);
//                    busList.add(model);
//                }
//                busAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//            }
//        });


//        databaseReference.child("Starting Point").equalTo("fromBus").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
//                        CustomRowItem busCustomRowItem = dataSnapshot.getValue(CustomRowItem.class);
//                        busList.add(busCustomRowItem);
//
//                    }
//
//                    busAdapter.notifyDataSetChanged();
//                }
//
//                databaseReference.child("Ending Point").equalTo("toBus").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                        if(snapshot.exists()){
//                            for(DataSnapshot dataSnapshot : snapshot.getChildren()){
//                                CustomRowItem busCustomRowItem = dataSnapshot.getValue(CustomRowItem.class);
//                                busList.add(busCustomRowItem);
//
//                            }
//
//                            busAdapter.notifyDataSetChanged();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
//                        Toast.makeText(BuslistActivity.this, "Firebase Database Error", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//                Toast.makeText(BuslistActivity.this, "Firebase Database Error", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//
//
//
//    }
//
//    ValueEventListener valueEventListener = new ValueEventListener() {
//        @Override
//        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//            if(snapshot.exists()){
//                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    CustomRowItem busCustomRowItem = dataSnapshot.getValue(CustomRowItem.class);
//                    busList.add(busCustomRowItem);
//
//                }
//
//                busAdapter.notifyDataSetChanged();
//            }
//        }
//
//        @Override
//        public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//        }
//    };

}