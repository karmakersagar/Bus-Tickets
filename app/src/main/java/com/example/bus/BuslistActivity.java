package com.example.bus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class BuslistActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    private int buses[] = {R.drawable.bus1,R.drawable.bus2,
            R.drawable.bus3,R.drawable.bus4,
            R.drawable.bus5,R.drawable.bus6};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buslist);
        String[] busesName = getResources().getStringArray(R.array.bus_name);
        String[] busesNumber = getResources().getStringArray(R.array.bus_number);
        String[] busesCondition = getResources().getStringArray(R.array.bus_codition);
        String[] busesFrom = getResources().getStringArray(R.array.from);
        String[] busesTo  = getResources().getStringArray(R.array.to);
        String[] busesJourneyDate = getResources().getStringArray(R.array.journey_date);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerId);
        List<CustomRowItem> list = new ArrayList<CustomRowItem>();

        for(int i = 0;i< busesName.length;i++){
            CustomRowItem c = new CustomRowItem(buses[i%6],busesName[i],
                    busesNumber[i],busesCondition[i],busesFrom[i],busesTo[i],busesJourneyDate[i]);
            list.add(c);
        }


        CustomAdapter customAdapter  = new CustomAdapter(BuslistActivity.this,list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(customAdapter);
    }
}