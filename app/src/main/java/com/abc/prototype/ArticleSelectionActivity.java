package com.abc.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
        source = extras.getString("source");
        category = extras.getString("category");
        subcategory = extras.getString("subcategory");


    }
}