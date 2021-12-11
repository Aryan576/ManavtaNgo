package com.manavta.ngoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MoreDetailActivity extends AppCompatActivity {

    TextView tvData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_detail);

       Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("More Info");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tvData = findViewById(R.id.tv_detail);
        Intent i = getIntent();
        String strName = i.getStringExtra("HOSPITAL_NAME");
        String strBed = i.getStringExtra("HOSPITAL_BEDS");
        String strPhone = i.getStringExtra("HOSPITAL_PHONE");
        String strAdd = i.getStringExtra("HOSPITAL_ADDRESS");
        String strPin = i.getStringExtra("HOSPITAL_PIN");
        tvData.setText(strName+"\n \n"+strBed+"\n \n"+strPhone+
                "\n \n"+strAdd+","+strPin);

    }
}