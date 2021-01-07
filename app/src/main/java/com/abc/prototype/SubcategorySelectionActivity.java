package com.abc.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class SubcategorySelectionActivity extends AppCompatActivity {

    TextView tv;
    private String source;
    private String category;
    private String subcategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategory_selection);

        tv = findViewById(R.id.tempy);

        Bundle extras = getIntent().getExtras();
        source = extras.getString("source");     // TODO: 05/01/2021 this may produce null pointerException -> di na kasi sa prev activity, sinigurado nang hindi makakalusot pag walang laman
        category = extras.getString("category");     // TODO: 05/01/2021 this may produce null pointerException -> di na kasi sa prev activity, sinigurado nang hindi makakalusot pag walang laman

        tv.setText(category);
    }
}