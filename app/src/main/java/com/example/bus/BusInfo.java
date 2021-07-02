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

public class BusInfo extends AppCompatActivity {

    private TextView busNameTextView, busPhoneTextView, busEmailTextView;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_info);
        auth = FirebaseAuth.getInstance();



        busNameTextView = findViewById(R.id.busNameText);
        busPhoneTextView = findViewById(R.id.busPhoneInfo);
        busEmailTextView = findViewById(R.id.busEmailText);

        Intent intent = getIntent();
        String busName = intent.getStringExtra("busName");
        busNameTextView.setText(busName);
        databaseReference = FirebaseDatabase.getInstance("https://buss-886c2-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("BusInfo").child(busName);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                BusInfoItem busInfoItem = snapshot.getValue(BusInfoItem.class);
                busPhoneTextView.setText(busInfoItem.getMobile());
                busEmailTextView.setText(busInfoItem.getEmail());
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }
}