package com.manavta.ngoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PlasmaDetailsActivity extends AppCompatActivity {
    TextView tvData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_detail);
        tvData = findViewById(R.id.tv_detail);


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



        Intent i = getIntent();
        String str_age= i.getStringExtra("age");
        String str_bloodgroup=   i.getStringExtra("bloodgroup");
        String str_city= i.getStringExtra("city");
        String str_dateofcovidnegative=i.getStringExtra("dateofcovidnegative");
        String str_dateofcovidpositive=i.getStringExtra("dateofcovidpositive");
        String str_gender= i.getStringExtra("gender");
        String str_name=i.getStringExtra("name");
        String str_phonenumber=i.getStringExtra("phonenumber");
        String str_state=i.getStringExtra("state");
        String str_weight= i.getStringExtra("weight");
        String str_plasmaid=i.getStringExtra("plasmaid");

        tvData.setText(str_name+"\n \n"+str_gender+"\n \n"+str_bloodgroup+"\n \n"+str_age+"\n \n"+str_weight+
                "\n \n"+str_phonenumber+"\n \n"+str_dateofcovidnegative+"\n \n"+str_dateofcovidpositive);

    }
}