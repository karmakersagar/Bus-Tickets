package com.example.bus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
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
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class Login extends AppCompatActivity {

    private EditText emailLog, passwordLog,resetEmail;
    private TextView registerView , forgetPasswordbtn;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private Button loginButtn;
    private AlertDialog.Builder forgetPassAlert;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailLog = findViewById(R.id.logInEmail);
        passwordLog = findViewById(R.id.logInPassword);
        registerView =  findViewById(R.id.createAccTxt);
        forgetPasswordbtn = findViewById(R.id.fogetPassword);
        firebaseAuth = FirebaseAuth.getInstance();
        forgetPassAlert = new AlertDialog.Builder(Login.this);
        inflater = this.getLayoutInflater();

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
                            Intent ig = new Intent(getApplicationContext(),MainActivity.class);
                            ig.putExtra("Password",logInPassword);
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

        forgetPasswordbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view1  = inflater.inflate(R.layout.reset_pass,null);
                forgetPassAlert.setTitle("Reset Forget Password ?").setMessage("Enter your email to get reset password link");
                forgetPassAlert.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        resetEmail = view1.findViewById(R.id.reset_passwordEditTxt);
                        String reEmail = resetEmail.getText().toString();
                        if(reEmail.isEmpty()){
                            resetEmail.setError("Email is Required !");
                            resetEmail.requestFocus();
                            return;
                        }

                        firebaseAuth.sendPasswordResetEmail(reEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(Login.this, "Reset Password Email is send", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

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

    @Override
    protected void onStart() {
        super.onStart();

        if(firebaseAuth.getCurrentUser()!= null && firebaseAuth.getCurrentUser().isEmailVerified()){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
    }
}