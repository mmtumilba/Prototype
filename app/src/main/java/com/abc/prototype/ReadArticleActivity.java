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

import static com.abc.prototype.Navigate.goToSourceSelection;

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
    private Button btnNext;


    private AbsScraper absScraper;
    private InquirerScraper inquirerScraper;

    private int index = 0;
    private int maxIndex;

    private String temp="";

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

        btnPrev = findViewById(R.id.button1);
        btnPrev.setAlpha((float) 0.5);
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                previousParagraph();
                if (index == 0) {
                    btnPrev.setAlpha((float) 0.5);
                }
                if (index == maxIndex) {
                    btnNext.setAlpha((float) 1);
                }
                if (index > 0) {
                    index--;
                    temp = article.get(index) + R.string.end_paragraph;
                    tv.setText(temp);

//                    tv.setText(article.get(index) + R.string.end_paragraph);
                    mTTS.stop();
                    TextReader.say(mTTS, tv);
                }
            }
        });


        btnNext = findViewById(R.id.button2);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                nextParagraph();
                if (index == maxIndex - 1) {
                    btnNext.setAlpha((float) 0.5);
//            index++;
//            tv.setText(article.get(index) + "end of paragraph.");
//            mTTS.stop();
//            TextReader.say(mTTS, tv);
                }
                else if (index == 0) {
                    btnPrev.setAlpha((float) 1);
                }
                else if (index < maxIndex) {
                    index++;
                    temp = article.get(index) + R.string.end_paragraph;
                    tv.setText(temp);
//                    tv.setText(article.get(index) + " end of paragraph");
                    mTTS.stop();
                    TextReader.say(mTTS, tv);
                }
            }
        });

        Button btnReadAgain = findViewById(R.id.button3);
        btnReadAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = 0;
                tv.setText(article.get(index));
                mTTS.stop();
                TextReader.say(mTTS, tv);
            }
        });

        Button btnNewArticle = findViewById(R.id.button4);
        btnNewArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                goToSourceSelection(context);
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

//            tv.setText(article.get(index) + "end of paragraph");
            mTTS.stop();
            TextReader.say(mTTS, tv);
        }
    }

    //// TODO: 13/01/2021 hint @ articleSelection change to @string/article

    private void nextParagraph() {
        if (index == maxIndex - 1) {
            btnNext.setAlpha((float) 0.5);
//            index++;
//            tv.setText(article.get(index) + "end of paragraph.");
//            mTTS.stop();
//            TextReader.say(mTTS, tv);
        }
        else if (index == 0) {
            btnPrev.setAlpha((float) 1);
        }
        else if (index < maxIndex) {
            index++;
//            tv.setText(article.get(index) + " end of paragraph");
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