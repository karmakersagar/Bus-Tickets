package com.example.bus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.bus.fragments.HomeFragment;
import com.example.bus.fragments.Profile;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private AlertDialog.Builder logOutBuilder,exitAppBuilder;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar)findViewById(R.id.toolbarId);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Main Activity");

        //setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);
        navigationView = findViewById(R.id.NavigationView);
        firebaseAuth = FirebaseAuth.getInstance();

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.Open,R.string.Close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent iin = getIntent();
        Bundle data = iin.getExtras();
        if(data!=null){
            String password = (String) data.get("Password");
            firebaseUser = firebaseAuth.getCurrentUser();
            String userId = firebaseUser.getUid();
            FirebaseDatabase.getInstance("https://buss-886c2-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users").child(userId).child("passWord").setValue(password);
        }
        showFragments(new HomeFragment());

        drawerLayout.closeDrawer(GravityCompat.START);
        //finish();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home :
                        Toast.makeText(MainActivity.this, "Home Menu is Clicked !!", Toast.LENGTH_SHORT).show();
                        showFragmentsss(new HomeFragment());
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.profile:
                        Toast.makeText(MainActivity.this, "Profile Menu is Clicked !!", Toast.LENGTH_SHORT).show();
                        showFragmentsss(new Profile());
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;

                    case R.id.notification:
                        Toast.makeText(MainActivity.this, "notification Menu is Clicked !!", Toast.LENGTH_SHORT).show();
                        Intent intentNoti = new Intent(getApplicationContext(),Notifications.class);
                        intentNoti.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intentNoti);
                        return true;
                    case R.id.tickets:
                        Toast.makeText(MainActivity.this, "Tickets Menu is Clicked !!", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.logout:
                        Toast.makeText(MainActivity.this, "Log out  Menu is Clicked !!", Toast.LENGTH_SHORT).show();
                         logOutBuilder = new AlertDialog.Builder(MainActivity.this);
                         //setTitle
                        logOutBuilder.setTitle("Log Out");
                        //Set message
                        logOutBuilder.setMessage("Kire Jabi Ga :v ???");
                        //positive yes button
                        logOutBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // log out
                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                                finish();
                            }
                        });

                        //log out cancel button
                        logOutBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //dismiss dialogue;
                                dialogInterface.dismiss();
                            }
                        });
                        // show  dialogue
                        logOutBuilder.show();
                        return true;
                }

                return true;
            }

        });
    }

    public void showFragments(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
    public void showFragmentsss(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return  true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
//           //else {exitAppBuilder = new AlertDialog.Builder(MainActivity.this);
//        exitAppBuilder.setMessage("Tui ki Asolei ber hoye jabi Ga ?").setCancelable(false)
//                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                             MainActivity.super.onBackPressed();
//                    }
//                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//            }
//        });
//        exitAppBuilder.show();
//        }

      /*  if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }*/

    }
}