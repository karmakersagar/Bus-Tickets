package com.example.bus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import org.jetbrains.annotations.NotNull;

public class Login extends AppCompatActivity {

    private EditText emailLog, passwordLog;
    private TextView registerView , forgetPasswordbtn;
    private FirebaseAuth firebaseAuth;

    private Button loginButtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailLog = findViewById(R.id.logInEmail);
        passwordLog = findViewById(R.id.logInPassword);
        registerView =  findViewById(R.id.createAccTxt);
        forgetPasswordbtn = findViewById(R.id.fogetPassword);
        firebaseAuth = FirebaseAuth.getInstance();

        loginButtn = findViewById(R.id.logInButton);

        registerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentg = new Intent(getApplicationContext(),Register.class);
                //intentg.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentg);
            }
        });

        loginButtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String logInEmail = emailLog.getText().toString();
                String logInPassword = passwordLog.getText().toString();
                if(logInEmail.isEmpty()){
                    emailLog.setError("Emnail is Misisng");
                    return;
                }
                if(logInPassword.isEmpty()){
                    passwordLog.setError("Paasword is Missing");
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(logInEmail,logInPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        if(firebaseAuth.getCurrentUser().isEmailVerified()){
                            Toast.makeText(Login.this, "Log In successfull ", Toast.LENGTH_SHORT).show();
                            Intent ig = new Intent(getApplicationContext(),Register.class);
                            //ig.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(ig);
                            finish();
                        }else{
                            Toast.makeText(Login.this, "Please Verify Your Email ", Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(Login.this, "Errpor " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


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