package com.example.hospie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class dashboard extends AppCompatActivity {


    private TextView myAppointments;
    private TextView contactUs;
    private TextView logOut;
    TextView dialogTextview;
    Context context;
    Button dialogButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        myAppointments = (TextView)findViewById(R.id.myappointments);
        contactUs = (TextView)findViewById(R.id.contact);
        logOut = (TextView)findViewById(R.id.logout);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard.this, loginPage.class);
            }
        });
        context = this;
        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.customdialog);
                dialogTextview = (TextView)findViewById(R.id.dialogtextview);
                dialogButton = dialog.findViewById(R.id.dialog_button);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent= new Intent(dashboard.this, homePage.class);
                    }
                });
                dialog.show();


            }
        });


        BottomNavigationView bottomNavigationView =findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.dashboard);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


                switch (menuItem.getItemId()){
                    case R.id.dashboard:
                        return true;

                    case R.id.appointment:
                        startActivity(new Intent(getApplicationContext(), appointment.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.clinic:
                        startActivity(new Intent(getApplicationContext(), about.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}