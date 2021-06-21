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
import android.widget.EditText;
import android.widget.TextView;

import com.example.bus.Buslist;
import com.example.bus.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    View view;
    private Button searchButton;
    private AutoCompleteTextView fromAutoCompleteTextView,toAutoCompleteTextView;
    private TextInputEditText journeyDateEditText;
    private TextInputLayout textInputLayout;
    private int journeyDate,journeyMonth,journeyYear;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

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
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()-1000);
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
                Intent intent = new Intent(getActivity(), Buslist.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        return view;
    }
}