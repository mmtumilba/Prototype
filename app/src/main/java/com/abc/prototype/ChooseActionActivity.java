package com.abc.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static com.abc.prototype.Navigate.goToReadArticleActivity;
import static com.abc.prototype.Navigate.goToSourceSelection;

// // TODO: 09/01/2021 needs more work :)) 
public class ChooseActionActivity extends AppCompatActivity {

    private String title;
    private String link;
    private Button btnRead;
    private Button btnBookmark;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_action);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            link = extras.getString("link");
            title = extras.getString("title");
        }

        btnRead = findViewById(R.id.buttonChooseActionRead);
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                goToReadArticleActivity(context, link, title);
            }
        });
        
        btnBookmark = findViewById(R.id.buttonChooseActionBookmark);
        btnBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { 
                Context context = getApplicationContext();
                goToSourceSelection(context);
            }
        });



    }
}