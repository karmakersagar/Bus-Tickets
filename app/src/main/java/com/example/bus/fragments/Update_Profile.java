package com.example.bus.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bus.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Update_Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Update_Profile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Update_Profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Update_Profile.
     */
    // TODO: Rename and change types and number of parameters
    private View view;
    private CircleImageView imageProfile;

    TextInputEditText firstName, lastName,emailEDit,password,Phone;
    private Button selectImageButton,updateButton;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    String fName,lName,email,passWord,phoneNo,userId;
    public static Update_Profile newInstance(String param1, String param2) {
        Update_Profile fragment = new Update_Profile();
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
        view =  inflater.inflate(R.layout.fragment_update__profile, container, false);
        initView(view);

        Bundle dataBundle = this.getArguments();
         fName = dataBundle.getString("firstName");
         lName = dataBundle.getString("lastName");
         email = dataBundle.getString("mail");
         phoneNo = dataBundle.getString("phone");
         passWord = dataBundle.getString("password");
         firebaseAuth = FirebaseAuth.getInstance();
         firebaseUser = firebaseAuth.getCurrentUser();
        firstName.setText(fName);
        lastName.setText(lName);
        emailEDit.setText(email);
        Phone.setText(phoneNo);
        password.setText(passWord);
        userId = firebaseAuth.getCurrentUser().getUid();

        databaseReference = FirebaseDatabase.getInstance("https://buss-886c2-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users").child(userId);

        emailEDit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Email cannot be changed", Toast.LENGTH_SHORT).show();
            }
        });




        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFirstNameChanged() || isLastNameChanged() || isPasswordChanged() || isPhoneNoChanged() ){
                    Toast.makeText(getActivity(), "Data has been Updated", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "data not changed ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
    private void initView(View view){
        firstName = view.findViewById(R.id.firstNameTxt);
        lastName = view.findViewById(R.id.lastNameTxt);
        imageProfile = view.findViewById(R.id.circleImage);
        selectImageButton = view.findViewById(R.id.selectImageButton);
        emailEDit = view.findViewById(R.id.emailTxt);
        Phone = view.findViewById(R.id.phoneTxt);
        password = view.findViewById(R.id.passwordTxt);
        updateButton = view.findViewById(R.id.updateButton);
    }

   /* public void UPDATE(View view){
        if(isFirstNameChanged() )
        {//|| isLastNameChanged() || isPasswordChanged() || isPhoneNoChanged()){
            Toast.makeText(getActivity(), "Data has been Updated", Toast.LENGTH_SHORT).show();
        }
    }*/

    private boolean isPhoneNoChanged() {
        if(!phoneNo.equals(Phone.getText().toString())){
            databaseReference.child("phoneNo").setValue(Phone.getText().toString());
            phoneNo = Phone.getText().toString();
            return true;
        }else {
            return false;
        }
   }
    private boolean isFirstNameChanged(){
        if(!fName.equals(firstName.getText().toString())){
            databaseReference.child("firstName").setValue(firstName.getText().toString());
            fName = firstName.getText().toString();
            return true;
        }else {
            return false;
        }

    }
    private boolean isLastNameChanged(){
        if(!lName.equals(lastName.getText().toString())){
            databaseReference.child("lastName").setValue(firstName.getText().toString());
            lName = lastName.getText().toString();
            return true;
        }else {
            return false;
        }

    }

    private boolean isPasswordChanged(){
        if(!passWord.equals(password.getText().toString()) ){

                AuthCredential authCredential = EmailAuthProvider.getCredential(firebaseUser.getEmail(),passWord);
                firebaseUser.reauthenticate(authCredential).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        firebaseUser.updatePassword(password.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                if((!password.getText().toString().isEmpty()) && password.getText().toString().length()>5){
                                    databaseReference.child("passWord").setValue(password.getText().toString());
                                    passWord = password.getText().toString();
                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(getActivity(), ""+ e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                return true;


        }else{
            return false;
        }
    }
}