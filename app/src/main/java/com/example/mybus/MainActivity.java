package com.example.mybus;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private View bus;
    private View mybus;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        FirebaseDatabase.getInstance().getReference().setValue("This is tracker app");


        setContentView(R.layout.activity_main);
        bus=findViewById(R.id.bus1);
        bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this,Bus1Maps_Activity.class);
                startActivity(intent);
            }
        });

        mybus=findViewById(R.id.mybus);
        mybus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this,Bus1UserMaps_Activity.class);
                startActivity(intent);
            }
        });


    }
}