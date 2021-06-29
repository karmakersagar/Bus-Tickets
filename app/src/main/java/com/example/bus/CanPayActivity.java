package com.example.bus;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

public class CanPayActivity extends AppCompatActivity {
    private TextView busNameTextView,journeyDateTextView,busConditionTextView,numberOfSeatsTextView,totalCostsTextView,fromTextView,toTextView;
    private Button button;
    private CardView bKashCardView,rocketCardView,mCashCardView,nagadCardView;
    private LayoutInflater layoutInflater;
    private View view;
    private String BusName,JourneyDate,BusCondition,FromCity, ToCity;
    private EditText userNumber;
    private EditText passWord;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_can_pay);
        Intent intent = getIntent();
        BusName = intent.getStringExtra("busName").toString();
        JourneyDate = intent.getStringExtra("journeyDate").toString();
        BusCondition = intent.getStringExtra("busCondition").toString();
        FromCity = intent.getStringExtra("fromCity").toString();
        ToCity = intent.getStringExtra("toCity").toString();
        String numberOfSeats = intent.getStringExtra("numberOfSeats").toString();
        String totalCosts = intent.getStringExtra("totalCosts").toString();
        Map<String,String> seatMap = (Map<String, String>)intent.getSerializableExtra("seatMap");
        String seatsName = "";

        for (Map.Entry<String, String> entry : seatMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if(value.equals("1")){
                seatsName = seatsName + " " + key;
            }
        }

        busNameTextView = (TextView)findViewById(R.id.busFareID);
        fromTextView =(TextView)findViewById(R.id.fromID);
        toTextView = (TextView)findViewById(R.id.toID);
        journeyDateTextView = (TextView)findViewById(R.id.busJourneyDateId);
        busConditionTextView = (TextView)findViewById(R.id.busConditionId);
        numberOfSeatsTextView = (TextView)findViewById(R.id.totalSeatId);
        totalCostsTextView = (TextView)findViewById(R.id.totalCostId);
        button = (Button)findViewById(R.id.payButtonId);

        bKashCardView = (CardView)findViewById(R.id.bkashCardViewId);
        mCashCardView = (CardView)findViewById(R.id.mCashCardViewId);
        rocketCardView = (CardView)findViewById(R.id.rocketCardViewId);
        nagadCardView = (CardView)findViewById(R.id.nagadCardViewId);


        layoutInflater = LayoutInflater.from(getApplicationContext());
        busNameTextView.setText(BusName);
        fromTextView.setText(FromCity);
        toTextView.setText(ToCity);
        journeyDateTextView.setText(JourneyDate);
        busConditionTextView.setText(BusCondition);
        numberOfSeatsTextView.setText(seatsName + " (" +numberOfSeats+")");
        totalCostsTextView.setText(totalCosts);


        view = layoutInflater.inflate(R.layout.payment_set,null);
        userNumber = view.findViewById(R.id.phoneNumberId);
        passWord = view.findViewById(R.id.passwordId);

        bKashCardView.setOnClickListener(this::onClick);
        nagadCardView.setOnClickListener(this::onClick);
        rocketCardView.setOnClickListener(this::onClick);
        mCashCardView.setOnClickListener(this::onClick);
        button.setOnClickListener(this::onClick);


    }

    public void onClick(View v) {

        if(v.getId() == R.id.bkashCardViewId){
            SetAlertDialogue("Bkash",R.drawable.bkash);
//            bKashCardView.setCardBackgroundColor(Color.parseColor("#6495ED"));
//            button.setVisibility(View.VISIBLE);
        }
        if(v.getId() == R.id.nagadCardViewId){
            SetAlertDialogue("Nagad",R.drawable.nagad);
//            nagadCardView.setCardBackgroundColor(Color.parseColor("#6495ED"));
//            button.setVisibility(View.VISIBLE);
        }
        if(v.getId() == R.id.mCashCardViewId){
            SetAlertDialogue("mCash",R.drawable.mcash);
//            mCashCardView.setCardBackgroundColor(Color.parseColor("#6495ED"));
//            button.setVisibility(View.VISIBLE);
        }
        if(v.getId() == R.id.rocketCardViewId){
            SetAlertDialogue("Rocket",R.drawable.rocket);
//            rocketCardView.setCardBackgroundColor(Color.parseColor("#6495ED"));
//            button.setVisibility(View.VISIBLE);
        }
        if(v.getId() == R.id.payButtonId){
            Intent intent1 = new Intent(getApplicationContext(),BookingFinish.class);
            intent1.putExtra("busName",BusName);
            intent1.putExtra("journeyDate",JourneyDate);
            intent1.putExtra("busCondition",BusCondition);
            if (!userNumber.getText().toString().isEmpty()){
                startActivity(intent1);
            }

        }

    }

    private void SetAlertDialogue(String name,int image){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CanPayActivity.this);
        alertDialogBuilder.setTitle(name);
        alertDialogBuilder.setIcon(image);
        alertDialogBuilder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });

        alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialogBuilder.setView(view);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }



}