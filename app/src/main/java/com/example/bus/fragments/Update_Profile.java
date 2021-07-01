package com.example.bus.fragments;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class Update_Profile extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri uri;

    private View view;
    private ImageView imageProfile;

    TextInputEditText firstName, lastName,emailEDit,password,Phone;
    private Button updateButton;
    private ImageButton selectImageButton;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference,databaseReference1;
    private FirebaseUser firebaseUser;
    private StorageReference storageReference;
    private Boolean isProfileImageChange = false;
    private String fName,lName,email,passWord,phoneNo,userId,profileImage = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_update__profile, container, false);
        initView(view);

        Bundle dataBundle = this.getArguments();
        fName = dataBundle.getString("firstName");
        lName = dataBundle.getString("lastName");
        email = dataBundle.getString("mail");
        phoneNo = dataBundle.getString("phone");
        passWord = dataBundle.getString("password");
        if(dataBundle.getString("profileImage") != null){
            profileImage = dataBundle.getString("profileImage");
        }
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firstName.setText(fName);
        lastName.setText(lName);
        emailEDit.setText(email);
        Phone.setText(phoneNo);
        password.setText(passWord);
        if(profileImage != null) {
            Picasso.get().load(profileImage).placeholder(R.drawable.tom).into(imageProfile);
        }
        userId = firebaseAuth.getCurrentUser().getUid();

        databaseReference = FirebaseDatabase.getInstance("https://buss-886c2-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users").child(userId);
        storageReference = FirebaseStorage.getInstance("gs://buss-886c2.appspot.com").getReference("profileImage");
        databaseReference1 = databaseReference.child("profileImage");


        emailEDit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Email cannot be changed", Toast.LENGTH_SHORT).show();
            }
        });

        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFirstNameChanged() || isLastNameChanged() || isPasswordChanged() || isPhoneNoChanged() || isProfileImageChange){
                    Toast.makeText(getActivity(), "Data has been Updated", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "data not changed ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }


    private void uploadFile() {

        if(uri != null){

            ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading Picture");
            progressDialog.setMessage("Wait ... until the uploading is successfully completed..");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            StorageReference fileReference = storageReference.child(System.currentTimeMillis()+"."+getFileExtension(uri));
            fileReference.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageUrl = uri.toString();
                                    databaseReference1.setValue(imageUrl);
                                    isProfileImageChange = true;
                                    progressDialog.dismiss();
                                }
                            });
                        }

                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot snapshot) {

                }
            });



        }else{
            Toast.makeText(getContext(),"No file Selected",Toast.LENGTH_SHORT).show();
        }


    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            uri = data.getData();
            Picasso.get().load(uri).placeholder(R.drawable.tom).into(imageProfile);
            uploadFile();
        }
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

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
}