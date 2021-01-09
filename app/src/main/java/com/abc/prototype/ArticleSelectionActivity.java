package com.abc.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
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

    private TextView tv;
    private EditText et;
    private TextToSpeech mTTS;

    private Button btnBack;
    private Button btnNext;
    private Button btnSubmit;

    private Vector<String> links;
    private Vector<String> titles;
    private Vector<String> titleSets;


    private int setNum = 1;
    private int setNumIndex;
    private int setMax;

    private int titlesNum;
    private int lastSetSize;
    private int article;

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


        Context context = getApplicationContext();
        mTTS = TextReader.initialize(context);
        // TODO: 09/01/2021 TextReader.say(mTTS, tv) kapag may laman na ang tv plug in at scraperThread

        tv = findViewById(R.id.textViewChooseArticle);
        et = findViewById(R.id.editTextNumberInputArticle);


        btnBack = findViewById(R.id.buttonArticleSelectionBack);
        btnBack.setAlpha((float) 0.5);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setNum == 2) {
                    btnBack.setAlpha((float) 0.5);
                } else if (setNum == setMax) {
                    btnNext.setAlpha((float) 1);
                }
                if (setNum > 1) {
                    setNum--;
                    setNumIndex = setNum - 1;
                    tv.setText(titleSets.get(setNumIndex));
                }
            }
        });

        btnNext = findViewById(R.id.buttonArticleSelectionNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = setMax - 1;
                if (setNum == (temp)) {
                    btnNext.setAlpha((float) 0.5);
                } else if (setNum == 1) {
                    btnBack.setAlpha((float) 1);
                }
                if (setNum < setMax) {
                    setNum++;
                    setNumIndex = setNum - 1;
                    tv.setText(titleSets.get(setNumIndex));
                }
                Log.e("setNum", Integer.toString(setNum));
            }
        });
        
        //// TODO: 09/01/2021 twice ang size sang titlesets

        btnSubmit = findViewById(R.id.buttonArticleSelectionSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et.length() == 0) {
                    TextReader.invalidInput(mTTS, tv);
                    return;
                }
                // get titles.size()
                // modulo5 and pinakalast
                // if last set (setNum => 1 && setNum <= size%5)
                String temp = et.getText().toString();
                article = Integer.parseInt(temp);
                if (lastSetSize == 0) {
                    if ( (article < 1) || (article > 5) ) {
                        TextReader.invalidInput(mTTS, tv);
                    } else {
                        //goToChooseActionActivity
                    }
                }


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

                    links = absScraper.links;
                    titles = absScraper.titles;
                    titleSets = absScraper.titleSets;
                    titlesNum = titles.size();

                    lastSetSize = titlesNum % 5;
                    setMax = absScraper.titleSets.size();
                    Log.e("setMax", Integer.toString(setMax));

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


        /**
         * this function sets the initial set of articles user will choose from
         * @param aVoid
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            switch (source) {
                case ABS:
                    tv.setText(absScraper.titleSets.get(0));
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
        }
    }


    // TODO: 08/01/2021 add bookmars and settings button to all activities na kailangan lagyan
}