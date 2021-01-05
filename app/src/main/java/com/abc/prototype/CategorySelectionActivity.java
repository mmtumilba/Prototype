package com.abc.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class CategorySelectionActivity extends AppCompatActivity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selection);

        Bundle extras = getIntent().getExtras();

        String source = extras.getString("source");     // TODO: 05/01/2021 this may produce null pointerException 
        tv = findViewById(R.id.textViewTemp);
        tv.setText(source);



    }
}