package com.example.bus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SeatChoose extends AppCompatActivity {
    private GridView gridView;
    private Button button;
    private TextView selectedSeatsTextView,totalCostTextView;
    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    Double seatPrice ;
    Double totalCost = 0.0;
    int totalSeats = 0;
    int[] isSelectSeat = new int[25];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_choose);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        gridView = (GridView)findViewById(R.id.gridViewId);
        button = (Button)findViewById(R.id.confirm_buttonId);
        selectedSeatsTextView = (TextView)findViewById(R.id.select_seat_TextViewId);
        totalCostTextView = (TextView)findViewById(R.id.total_cost_TextViewId);
        auth = FirebaseAuth.getInstance();



        Intent intent = getIntent();
        String busId = intent.getStringExtra("busID").toString();
        String BusName = intent.getStringExtra("busName").toString();
        String StartPoint = intent.getStringExtra("start").toString();
        String EndPoint = intent.getStringExtra("end").toString();
        String Time = intent.getStringExtra("time").toString();
        String Fare =intent.getStringExtra("fare").toString();
        String Type = intent.getStringExtra("type").toString();
        String journeyDate = intent.getStringExtra("journeyDate").toString();
        seatPrice =Double.parseDouble(Fare) ;
        databaseReference = firebaseDatabase.getInstance("https://buss-886c2-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("SeatDetails").child(busId).child(journeyDate);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists() ){
                    //do something
                }
                else{
                    //create database
                    int index;
                    for(  index = 1; index<=24; index++){
                        databaseReference.child("A" + index).setValue(0);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        List<CustomGrid> list = new ArrayList<CustomGrid>();

        for(int i = 1 ; i <= 24 ; i++){
            list.add(new CustomGrid(R.drawable.seat,"A"+i));
        }

        CustomAdapterGrid adapter = new CustomAdapterGrid(SeatChoose.this,list);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(isSelectSeat[position] == 0){
                    view.setBackgroundColor(Color.parseColor("#00FF00"));
                    totalCost += seatPrice;
                    ++totalSeats;
                    Toast.makeText(getApplicationContext(), "You Selected Seat Number :" + (position + 1), Toast.LENGTH_SHORT).show();
                    isSelectSeat[position] = 1;
                }else{
                    view.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    totalCost -= seatPrice;
                    --totalSeats;
                    Toast.makeText(getApplicationContext(), "You Unselected Seat Number :" + (position + 1), Toast.LENGTH_SHORT).show();
                    isSelectSeat[position] = 0;
                }
                totalCostTextView.setText("Total Cost:" + totalCost+"");
                selectedSeatsTextView.setText("Number of selected Seats:"+totalSeats+"");
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPay  = new Intent(getApplicationContext(),CanPayActivity.class);
                intentPay.putExtra("fromCity",StartPoint);
                intentPay.putExtra("toCity",EndPoint);
                intentPay.putExtra("busName",BusName);
                intentPay.putExtra("journeyDate",journeyDate);
                intentPay.putExtra("busCondition",Type);
                intentPay.putExtra("numberOfSeats",Integer.toString(totalSeats));
                intentPay.putExtra("totalCosts",Double.toString(totalCost));
                startActivity(intentPay);
            }
        });


    }
}