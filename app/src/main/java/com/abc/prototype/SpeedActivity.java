package com.abc.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SpeedActivity extends AppCompatActivity {

    private TextView tv;
    private Button btnSlow;
    private Button btnNormal;
    private Button btnFast;

    // create a static variable for the speed


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed);

        tv =findViewById(R.id.textViewSpeed);

        btnSlow = findViewById(R.id.buttonSpeedSlow);
        btnSlow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextReader.speed = (float) 0.5;
            }
        });

        btnNormal = findViewById(R.id.buttonSpeedNormal);
        btnNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextReader.speed = (float) 1;
            }
        });

        btnFast = findViewById(R.id.buttonSpeedFast);
        btnFast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextReader.speed = (float) 1.5;
            }
        });
    }
}