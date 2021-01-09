package com.abc.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.Vector;

public class ArticleSelectionActivity extends AppCompatActivity {

    private String source;
    private String category;
    private String subcategory;
    private String link = "";

    private final String ABS = "abs";
    private final String GMA = "gma";
    private final String INQUIRER = "inquirer";
    private final String PHILSTAR = "philstar";

    private AbsScraper absScraper;
    private Vector <String> links;
    private Vector <String> titles;
    private Vector <String> titleSets;

    private TextView tv;
    private EditText et;
    private Button btnBack;
    private Button btnNext;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_selection);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            source = extras.getString("source");         // TODO: 05/01/2021 this may produce null pointerException -> di na kasi sa prev activity, sinigurado nang hindi makakalusot pag walang laman
            category = extras.getString("category");
            subcategory = extras.getString("subcategory");

            new ScraperThread().execute();
        }


        tv = findViewById(R.id.textViewChooseArticle);
        et = findViewById(R.id.editTextNumberInputArticle);


        btnBack = findViewById(R.id.buttonArticleSelectionBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnNext = findViewById(R.id.buttonArticleSelectionNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnSubmit = findViewById(R.id.buttonArticleSelectionSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }

    // TODO: 08/01/2021 Warning: This 'AsyncTask' class should be static or leaks might occur (com.abc.prototype.ArticleSelectionActivity.ScraperThread)
    private class ScraperThread extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            switch (source) {
                case ABS:
                    absScraper = new AbsScraper(category);
                    break;

//                case GMA:
//
//                    break;
//
//                case INQUIRER:
//
//                    break;
//
//                case PHILSTAR:
//
//                    break;
            }
            return null;
        }

        //sa background pa lang gawin mo na yung pag assign ng gagawing text


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            switch (source) {
                case ABS:
                    tv.setText(absScraper.titleSets.get(0));
                    break;

                case GMA:

                    break;

                case INQUIRER:

                    break;

                case PHILSTAR:

                    break;
            }
        }
    }


    // TODO: 08/01/2021 add bookmars and settings button to all activities na kailangan lagyan
}