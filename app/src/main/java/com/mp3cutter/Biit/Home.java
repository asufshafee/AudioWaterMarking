package com.mp3cutter.Biit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.mp3cutter.Biit.Activities.RingdroidSelectActivity;
import com.mp3cutter.Biit.R;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setTitle("Home");

        findViewById(R.id.New).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RingdroidSelectActivity.class));

            }
        });
        findViewById(R.id.Old).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(), RingdroidSelectActivity.class);
                intent.putExtra("Marked","true");
                startActivity(intent);

            }
        });
    }
}
