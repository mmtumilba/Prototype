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

import static com.abc.prototype.Navigate.goToChooseActionActivity;

public class ArticleSelectionActivity extends AppCompatActivity {

    private String source;
    private String category;
    private String subcategory;
    private String link = "";
    private String title = "";

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


        final Context context = getApplicationContext();
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
                mTTS.stop();
                TextReader.say(mTTS, tv);
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
                mTTS.stop();
                TextReader.say(mTTS, tv);
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

                String temp = et.getText().toString();
                article = Integer.parseInt(temp);

                if (lastSetSize == 0) {
                    if ( (article < 1) || (article > 5) ) {
                        TextReader.invalidInput(mTTS, tv);
                    } else {
                        getArticleDetails();
                        goToChooseActionActivity(context, link, title);
                    }
                } else {
                    if (setNum == setMax) {
                        if ( (article < 1) || (article > lastSetSize) ) {
                            TextReader.invalidInput(mTTS, tv);
                        } else {
                            getArticleDetails();
                            goToChooseActionActivity(context, link, title);
                        }
                    } else {
                        if ( (article < 1) || (article > 5) ) {
                            TextReader.invalidInput(mTTS, tv);
                        } else {
                            getArticleDetails();
                            goToChooseActionActivity(context, link, title);
                        }
                    }
                }
                mTTS.stop();
            }
        });



    }

    private void getArticleDetails() {
        int temp = setNumIndex * 5;
        link = links.get(temp);
        title = titles.get(temp);
        Log.e("title", title);
        Log.e("link", link);
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