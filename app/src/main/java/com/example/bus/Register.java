package com.example.bus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;


public class Register extends AppCompatActivity {

    private EditText userFirstName, userLastName, userEmail,userPassword,userPhoneNo;
    private Button userRegisterBtn;
    private TextView userLogInView;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userFirstName = findViewById(R.id.FirstName);
        userLastName = findViewById(R.id.LastName);
        userEmail = findViewById(R.id.logInEmail);
        userPassword = findViewById(R.id.logInPassword);
        userPhoneNo = findViewById(R.id.phone);
        userLogInView = findViewById(R.id.createAccTxt);
        userRegisterBtn = findViewById(R.id.logInButton);
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()!= null && firebaseAuth.getCurrentUser().isEmailVerified()){
            Toast.makeText(Register.this, "User Created  ", Toast.LENGTH_SHORT).show();
            Intent ie = new Intent(Register.this,Login.class);
            ie.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(ie);
            finish();
        }
        userLogInView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this,Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        userRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = userFirstName.getText().toString();
                String lastName = userLastName.getText().toString();
                String emailId = userEmail.getText().toString();
                String password = userPassword.getText().toString();
                String phoneNo = userPhoneNo.getText().toString();

                if(firstName.isEmpty()){
                    userFirstName.setError("First Name is Requied ! ");
                    userFirstName.requestFocus();
                    return;
                }
                if(lastName.isEmpty()){
                    userLastName.setError("Last Name is Required !");
                    userLastName.requestFocus();
                    return;
                }
                if(emailId.isEmpty()){
                    userEmail.setError("Email id Required !");
                    userEmail.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(emailId).matches()){
                    userEmail.setError("Please provide a Valid Email !");
                    userEmail.requestFocus();
                    return;

                }
                if(password.length() < 6){
                    userPassword.setError("Password must be greater or equal 6 characters !");
                    userPassword.requestFocus();
                    return;
                }
                if(phoneNo.isEmpty()){
                    userPhoneNo.setError("Phone is Required !");
                    userPhoneNo.requestFocus();
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(emailId,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        firebaseUser = firebaseAuth.getCurrentUser();


                       firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(Register.this, "verify email !!", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(Register.this,Login.class);
                                    i.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(i);
                                    finish();

                                }else{
                                    Toast.makeText(Register.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        String userId = firebaseUser.getUid();
                        Users users = new Users(firstName,lastName,emailId,password,phoneNo);
                        FirebaseDatabase.getInstance().getReference("users").child(userId).setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(Register.this, "database inserted", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(Register.this, "databse not working", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(Register.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });




    }
}