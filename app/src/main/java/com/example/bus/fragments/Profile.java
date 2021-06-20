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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile.
     */
    // TODO: Rename and change types and number of parameters
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



    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
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
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", error.toException());


            }
        });

        return view;
    }
}