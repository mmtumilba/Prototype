package com.abc.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ReadArticleActivity extends AppCompatActivity {

    private String title;
    private String link;

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_article);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            link = extras.getString("link");
            title = extras.getString("title");

            Log.e("link in ReadArticle", link);
            Log.e("title in ReadArticle", title);
        }

        tv = findViewById(R.id.textViewReadArticle);
        tv.setText(title);
    }
}