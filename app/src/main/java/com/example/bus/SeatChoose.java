package com.example.bus;

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

import java.util.ArrayList;
import java.util.List;

public class SeatChoose extends AppCompatActivity {
    private GridView gridView;
    private Button button;
    private TextView selectedSeatsTextView,totalCostTextView;
    Double seatPrice = 900.00;
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


        Intent intent = getIntent();
        String BusName = intent.getStringExtra("busName").toString();
        String JourneyDate = intent.getStringExtra("journeyDate").toString();
        String BusCondition = intent.getStringExtra("busCondition").toString();

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
                Intent intent1  = new Intent(getApplicationContext(),CanPayActivity.class);
                intent1.putExtra("busName",BusName);
                intent1.putExtra("journeyDate",JourneyDate);
                intent1.putExtra("busCondition",BusCondition);
                intent1.putExtra("numberOfSeats",Integer.toString(totalSeats));
                intent1.putExtra("totalCosts",Double.toString(totalCost));
                startActivity(intent1);
            }
        });


    }
}