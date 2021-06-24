package com.example.bus.fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.bus.BuslistActivity;
import com.example.bus.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class HomeFragment extends Fragment {
    View view;
    private Button searchButton;
    private AutoCompleteTextView fromAutoCompleteTextView,toAutoCompleteTextView;
    private TextInputEditText journeyDateEditText;
    private TextInputLayout textInputLayout;
    private int journeyDate,journeyMonth,journeyYear;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        fromAutoCompleteTextView = view.findViewById(R.id.from_auto_completeTextViewId);
        toAutoCompleteTextView  = view.findViewById(R.id.to_auto_completeTextViewId);
        journeyDateEditText = view.findViewById(R.id.journeydate_editTextViewId);
        textInputLayout = view.findViewById(R.id.textInputLayoutId);
        searchButton = view.findViewById(R.id.search_busesId);
        String[] from = getResources().getStringArray(R.array.from);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),R.layout.dropdown_item,from);
        fromAutoCompleteTextView.setAdapter(arrayAdapter);

        String[] to = getResources().getStringArray(R.array.from);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(getActivity(),R.layout.dropdown_item,to);
        toAutoCompleteTextView.setAdapter(arrayAdapter);

        journeyDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                journeyDate = calendar.get(Calendar.DATE);
                journeyMonth = calendar.get(Calendar.MONTH);
                journeyYear = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        android.R.style.Theme_DeviceDefault_Dialog,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                                journeyDateEditText.setText(date+" - "+month+" - "+year);
                            }
                        },
                        journeyYear,
                        journeyMonth,
                        journeyDate);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);;
                datePickerDialog.show();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fromCity = fromAutoCompleteTextView.getText().toString().toLowerCase();
                String toCity = toAutoCompleteTextView.getText().toString().toLowerCase();
                String date = journeyDateEditText.getText().toString().toLowerCase();
                if(fromCity.isEmpty()){
                    fromAutoCompleteTextView.setError("From City is Required");
                    fromAutoCompleteTextView.requestFocus();
                    return;
                }
                if(toCity.isEmpty()){
                    toAutoCompleteTextView.setError("To City is required");
                    toAutoCompleteTextView.requestFocus();
                    return;
                }
                if(date.isEmpty()){
                    journeyDateEditText.setError("Journey date is required");
                    journeyDateEditText.requestFocus();
                    return;
                }

                Intent intent = new Intent(getActivity(), BuslistActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        return view;
    }
}