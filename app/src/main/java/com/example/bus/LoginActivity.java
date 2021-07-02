package com.example.bus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private AlertDialog.Builder forgetPassAlert;
    private LayoutInflater inflater;


    private TextInputLayout textInputEmail,textInputPass;
    private Button loginButton,forgetPasswordButton;
    private TextView needAcount;
    private RelativeLayout relativeLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Initializations();

        toolbar = (Toolbar) findViewById(R.id.logintoolbarId);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Log In");

        firebaseAuth = FirebaseAuth.getInstance();
        forgetPassAlert = new AlertDialog.Builder(LoginActivity.this);
        inflater = this.getLayoutInflater();

        needAcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(validateEmail() && validatePassword())
                {
                    String emailText = textInputEmail.getEditText().getText().toString().trim();
                    String passText = textInputPass.getEditText().getText().toString().trim();

                    successFullLogIn(emailText,passText);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Error...", Toast.LENGTH_SHORT).show();
                    return;
                }


            }
        });

        forgetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View view1  = inflater.inflate(R.layout.reset_pass,null);
                forgetPassAlert.setTitle("Reset Forget Password ?").setMessage("Enter your email to get reset password link");


                forgetPassAlert.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final EditText resetEmailEditText = view1.findViewById(R.id.reset_passwordEditTxt);

                        String resetEmailText = resetEmailEditText.getText().toString();

                        if(resetEmailText.isEmpty()){
                            resetEmailEditText.setError("Email is Required !");
                            resetEmailEditText.requestFocus();
                            return;
                        }
                        resetForgetPassword(resetEmailText);
                    }
                });



                forgetPassAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                forgetPassAlert.setView(view1);
                forgetPassAlert.show();
            }
        });

    }

    private void resetForgetPassword(String resetEmailText) {
        firebaseAuth.sendPasswordResetEmail(resetEmailText).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(LoginActivity.this, "Reset Password Email is send", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean validateEmail(){
        String emailInput = textInputEmail.getEditText().getText().toString().trim();
        if(TextUtils.isEmpty(emailInput)){
            textInputEmail.setError("Field cann't be empty...");
            return false;
        }
        else {
            textInputEmail.setError(null);
            textInputEmail.setErrorEnabled(false);
            return true;
        }
    }


    private boolean validatePassword(){
        String passwordInput = textInputPass.getEditText().getText().toString().trim();
        if(TextUtils.isEmpty(passwordInput)){
            textInputPass.setError("Field cann't be empty...");
            return false;
        }
        else {
            textInputPass.setError(null);
            return true;
        }
    }


    private void successFullLogIn(String emailText,String passText){

        firebaseAuth.signInWithEmailAndPassword(emailText,passText).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                if(firebaseAuth.getCurrentUser().isEmailVerified()){
                    //Toast.makeText(LoginActivity.this, "Log In successfull ", Toast.LENGTH_SHORT).show();

                    Intent ig = new Intent(getApplicationContext(),MainActivity.class);
                    ig.putExtra("Password",passText);

                    startActivity(ig);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "Please Verify Your Email ", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(LoginActivity.this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void Initializations() {
        textInputEmail = (TextInputLayout)findViewById(R.id.loginEmailId);
        textInputPass = (TextInputLayout)findViewById(R.id.loginPasswordId);
        loginButton = (Button)findViewById(R.id.loginButtonId);
        needAcount = (TextView)findViewById(R.id.needAcountId);
        forgetPasswordButton  = (Button)findViewById(R.id.forgetPasswordId);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseAuth.getCurrentUser()!= null && firebaseAuth.getCurrentUser().isEmailVerified()){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
    }
}