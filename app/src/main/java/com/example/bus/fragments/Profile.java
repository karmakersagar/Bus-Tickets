package com.example.bus.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bus.R;
import com.example.bus.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;


public class Profile extends Fragment  {


       private View view;
       private CircleImageView imageProfile;
       private TextView fullName;
       private ImageView emailImage;
       private TextView textEmail;
       private ImageView passImage;
       private TextView passText;
       private ImageView imagePhone;
       private TextView textPhone;
       private Button editButton;
       private FirebaseAuth firebaseAuth;
       private FirebaseDatabase firebaseDatabase;
       private DatabaseReference databaseReference;
       private FirebaseUser firebaseUser;

       String fName,lName,mail,pass,phn;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_profile, container, false);
        imageProfile = view.findViewById(R.id.mainImage);
        fullName = view.findViewById(R.id.fullText);
        emailImage = view.findViewById(R.id.emailImage);
        textEmail = view.findViewById(R.id.eamilTxt);
        imagePhone = view.findViewById(R.id.phoneImage);
        textPhone = view.findViewById(R.id.phoneTxt);
        passImage = view.findViewById(R.id.paswordImage);
        passText = view.findViewById(R.id.passwordTxt);
        editButton = view.findViewById(R.id.editButton);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        String userId = firebaseUser.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance("https://buss-886c2-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseReference = firebaseDatabase.getReference("users").child(userId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Users userProfile = snapshot.getValue(Users.class);
                fullName.setText(userProfile.getFirstName() +" " + userProfile.getLastName());
                textEmail.setText(userProfile.getEmailId());
                textPhone.setText(userProfile.getPhoneNo());
                passText.setText(userProfile.getPassWord());
                fName = userProfile.getFirstName();
                lName = userProfile.getLastName();

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", error.toException());


            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mail = textEmail.getText().toString().trim();

            Bundle dataBundle = new Bundle();
            dataBundle.putString("firstName",fName);
            dataBundle.putString("lastName",lName);
            dataBundle.putString("mail",mail);
            dataBundle.putString("phone",textPhone.getText().toString());
            dataBundle.putString("password",passText.getText().toString());
            Update_Profile update_profile = new Update_Profile();
            update_profile.setArguments(dataBundle);
            getFragmentManager().beginTransaction().replace(R.id.frameLayout, update_profile).addToBackStack(null).commit();
                }

        });



        return view;
    }


}