package com.abc.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.util.Printer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Vector;

import dalvik.annotation.TestTarget;

public class ReadArticleActivity extends AppCompatActivity {

    private String source;
    private String title;
    private String link;
    private String category;
    private String subcategory;


    private Vector<String> article;

    private final String ABS = "abs";
    private final String GMA = "gma";
    private final String INQUIRER = "inquirer";
    private final String PHILSTAR = "philstar";

    private TextView tv;
    private TextToSpeech mTTS;

    private Button btnPrev;
    private Button btnPausePlay;
    private Button btnNext;


    private AbsScraper absScraper;
    private InquirerScraper inquirerScraper;

    private int index = 0;
    private int maxIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_article);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            source = extras.getString("source");
            link = extras.getString("link");
            title = extras.getString("title");

            category = extras.getString("category");
            subcategory = extras.getString("subcategory");




            new ScraperThread().execute();
        }

        Context context = getApplicationContext();
        mTTS = TextReader.initialize(context);

        tv = findViewById(R.id.textViewReadArticle);

        btnPrev = findViewById(R.id.buttonReadArticlePreviousArticle);
        btnPrev.setAlpha((float) 0.5);
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousParagraph();

            }
        });

        btnPausePlay = findViewById(R.id.buttonReadArticlePausePlay);
        btnPausePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnNext = findViewById(R.id.buttonReadArticleNextArticle);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextParagraph();

            }
        });




    }

    private void previousParagraph() {
        if (index == 0) {
            btnPrev.setAlpha((float) 0.5);
        }
        if (index == maxIndex) {
            btnNext.setAlpha((float) 1);
        }
        if (index > 0) {
            index--;
            tv.setText(article.get(index));
            mTTS.stop();
            TextReader.say(mTTS, tv);
        }
    }

    //// TODO: 13/01/2021 hint @ articleSelection change to @string/article

    private void nextParagraph() {
        if (index == maxIndex - 1) {
            btnNext.setAlpha((float) 0.5);
        }
        if (index == 0) {
            btnPrev.setAlpha((float) 1);
        }
        if (index < maxIndex) {
            index++;
            tv.setText(article.get(index));
            mTTS.stop();
            TextReader.say(mTTS, tv);
        }
    }

    private class ScraperThread extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            switch (source) {
                case ABS:
                    absScraper = new AbsScraper(source, link);
                    article = absScraper.article;
                    maxIndex = article.size() - 1;

                    break;

//                case GMA:
//
//                    break;
//
                case INQUIRER:
                    inquirerScraper = new InquirerScraper(category, subcategory, link);
                    article = inquirerScraper.articleVector;
                    maxIndex = article.size() - 1;
                    break;
//
//                case PHILSTAR:
//
//                    break;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            switch (source) {
                case ABS:
                    tv.setText(article.get(index));
                    TextReader.say(mTTS, tv);
                    break;
//                case GMA:
//
//                    break;
//
                case INQUIRER:
                    tv.setText(article.get(index));
                    TextReader.say(mTTS, tv);
                    break;
//
//                case PHILSTAR:
//
//                    break;
            }
        }
    }

    // TODO: 11/01/2021 paano kapag gusto ni user tuloy tuloy lang ang pagbasa ng article

    @Override
    protected void onPostResume() {
        super.onPostResume();
        TextReader.say(mTTS, tv);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mTTS.stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mTTS.stop();
    }

}