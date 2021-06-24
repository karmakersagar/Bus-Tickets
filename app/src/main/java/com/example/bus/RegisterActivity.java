package com.example.bus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;


public class RegisterActivity extends AppCompatActivity {

//    private EditText userFirstName, userLastName, userEmail,userPassword,userPhoneNo;
//    private Button userRegisterBtn;
//    private TextView userLogInView;

    private TextInputLayout textInputEmail,textInputPass,textInputFirstName,textInputLastName,textInputPhone;
    private Button registerButton;
    private Toolbar toolbar;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Initilizations();


        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()!= null && firebaseAuth.getCurrentUser().isEmailVerified()){
            Toast.makeText(RegisterActivity.this, "User Created  ", Toast.LENGTH_SHORT).show();
            Intent ie = new Intent(RegisterActivity.this, LoginActivity.class);
            ie.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(ie);
            finish();
        }



        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = textInputFirstName.getEditText().getText().toString();
                String lastName = textInputLastName.getEditText().getText().toString();
                String emailText = textInputEmail.getEditText().getText().toString();
                String passwordText = textInputPass.getEditText().getText().toString();
                String phoneNoText = textInputPhone.getEditText().getText().toString();

                if(TextUtils.isEmpty(firstName)){
                    textInputFirstName.setError("First Name is Requied ! ");
                    textInputFirstName.requestFocus();
                    return;
                }else{
                    textInputFirstName.setErrorEnabled(false);
                    textInputFirstName.setError(null);
                }

                if(TextUtils.isEmpty(lastName)){
                    textInputLastName.setError("Last Name is Required !");
                    textInputLastName.requestFocus();
                    return;
                }else{
                    textInputLastName.setErrorEnabled(false);
                    textInputLastName.setError(null);
                }

                if(TextUtils.isEmpty(emailText)){
                    textInputEmail.setError("Email is Required !");
                    textInputEmail.requestFocus();
                    return;
                }else{
                    textInputEmail.setErrorEnabled(false);
                    textInputEmail.setError(null);
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
                    textInputEmail.setError("Please provide a Valid Email !");
                    textInputEmail.requestFocus();
                    return;

                }else{
                    textInputEmail.setErrorEnabled(false);
                    textInputEmail.setError(null);
                }
                if(passwordText.length() <= 6){
                    textInputPass.setError("Password must be greater or equalto 6 characters!");
                    textInputPass.requestFocus();
                    return;
                }else{
                    textInputPass.setErrorEnabled(false);
                    textInputPass.setError(null);
                }



                if(TextUtils.isEmpty(phoneNoText)){
                    textInputPhone.setError("Phone is Required !");
                    textInputPhone.requestFocus();
                    return;
                }else{
                    textInputPhone.setErrorEnabled(false);
                    textInputPhone.setError(null);
                }

                firebaseAuth.createUserWithEmailAndPassword(emailText,passwordText).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        firebaseUser = firebaseAuth.getCurrentUser();

                       firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(RegisterActivity.this, "Plz verify email !!", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                                    i.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(i);
                                    finish();

                                }else{
                                    Toast.makeText(RegisterActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        String userId = firebaseUser.getUid();
                        Users users = new Users(firstName,lastName,emailText,passwordText,phoneNoText);



                        FirebaseDatabase.getInstance("https://buss-886c2-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users").child(userId).setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(RegisterActivity.this, "database inserted", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(RegisterActivity.this, "databse not working", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(RegisterActivity.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

    private void Initilizations() {
        textInputEmail = (TextInputLayout)findViewById(R.id.emailId);
        textInputPass  = (TextInputLayout)findViewById(R.id.passwordId);
        registerButton = (Button)findViewById(R.id.registerButtonId);
        textInputPhone = (TextInputLayout)findViewById(R.id.phoneNumberId);
        textInputFirstName = (TextInputLayout)findViewById(R.id.firstNameId);
        textInputLastName = (TextInputLayout)findViewById(R.id.lastNameId);

    }
}