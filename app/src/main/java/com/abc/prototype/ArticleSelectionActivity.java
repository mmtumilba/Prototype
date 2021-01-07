package com.abc.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ArticleSelectionActivity extends AppCompatActivity {

    private String source;
    private String category;
    private String subcategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_selection);


        Bundle extras = getIntent().getExtras();
        source = extras.getString("source");         // TODO: 05/01/2021 this may produce null pointerException -> di na kasi sa prev activity, sinigurado nang hindi makakalusot pag walang laman
        category = extras.getString("category");
        subcategory = extras.getString("subcategory");

        TextView tv = findViewById(R.id.category);
        tv.setText(category);
        Log.e("source", source);


    }
}