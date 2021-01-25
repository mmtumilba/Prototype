package com.abc.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Vector;

import static com.abc.prototype.Navigate.goToCategorySelectionActivity;
import static com.abc.prototype.Navigate.goToReadArticleActivity;
import static com.abc.prototype.Navigate.goToSourceSelection;
import static com.abc.prototype.Navigate.goToSubcategorySelectionActivity;

public class ArticleSelectionActivity extends AppCompatActivity {

    private String source;
    private String category;
    private String subcategory;
    private String link = "";
    private String title = "";

    private String tempy;

    private final String ABS = "abs";
    private final String GMA = "gma";
    private final String INQUIRER = "inquirer";
    private final String PHILSTAR = "philstar";

    private AbsScraper absScraper;
    private InquirerScraper inquirerScraper;

    private TextView tv;
    private TextToSpeech mTTS;

    private Button btnBack;
    private Button btnNext;
    private Button btnPrev;


    private Vector<String> links;
    private Vector<String> titles;
    private Vector<String> titleSets;


    private int setNum = 1;
    private int setNumIndex;
    private int setMax;

    private int titlesNum;
    private int lastSetSize;
    private int article = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_selection);

        Context context = getApplicationContext();
        mTTS = TextReader.initialize(context);

        tv = findViewById(R.id.textViewChooseArticle);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            source = extras.getString("source");         // TODO: 05/01/2021 this may produce null pointerException -> di na kasi sa prev activity, sinigurado nang hindi makakalusot pag walang laman
            tempy = getText(R.string.fetch) + source;
            tv.setText(tempy);
            TextReader.say(mTTS, tv);

            category = extras.getString("category");
            subcategory = extras.getString("subcategory");

            new ScraperThread().execute();
        } else {
            emptyExtras();
        }
        // TODO: 09/01/2021 TextReader.say(mTTS, tv) kapag may laman na ang tv plug in at scraperThread


        if (lastSetSize == 0) {
            assign5buttons();
        }



        btnPrev = findViewById(R.id.buttonArticleSelectionPrev);
        btnPrev.setAlpha((float) 0.5);
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setNum == 2) {
                    btnPrev.setAlpha((float) 0.5);
                } else if (setNum == setMax) {
                    btnNext.setAlpha((float) 1);
                }
                if (setNum > 1) {
                    setNum--;
                    setNumIndex = setNum - 1;
                    tempy = getText(R.string.choose_article) + titleSets.get(setNumIndex);
                    tv.setText(tempy);
                    mTTS.stop();
                    TextReader.say(mTTS, tv);
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
                    btnPrev.setAlpha((float) 1);
                }
                if (setNum < setMax) {
                    setNum++;
                    setNumIndex = setNum - 1;
                    tempy = getText(R.string.choose_article) + titleSets.get(setNumIndex);
                    tv.setText(tempy);
                    mTTS.stop();
                    TextReader.say(mTTS, tv);
                }

            }
        });

        btnBack = findViewById(R.id.buttonArticleSelectionBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTTS.stop();
                if (source.equals(ABS)){
                    Context context = getApplicationContext();
                    goToCategorySelectionActivity(context, source);
                } else if (source.equals(INQUIRER)) {
                    Context context = getApplicationContext();
                    goToSubcategorySelectionActivity(context, source, category);
                }
            }
        });
    }

    private void assign5buttons () {
        Button btn1 = findViewById(R.id.button1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTTS.stop();
                article = 1;
                Context context = getApplicationContext();
                submit(context);
            }
        });

        Button btn2 = findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTTS.stop();
                article = 2;
                Context context = getApplicationContext();
                submit(context);
            }
        });

        Button btn3 = findViewById(R.id.button3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTTS.stop();
                article = 3;
                Context context = getApplicationContext();
                submit(context);
            }
        });

        Button btn4 = findViewById(R.id.button4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTTS.stop();
                article = 4;
                Context context = getApplicationContext();
                submit(context);
            }
        });

        Button btn5 = findViewById(R.id.button5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTTS.stop();
                article = 5;
                Context context = getApplicationContext();
                submit(context);
            }
        });
    }

    private void emptyExtras () {
        Context context = getApplicationContext();
        goToSourceSelection(context);
    }

    private void submit(Context context) {
        if (lastSetSize == 0) {
            if ( (article < 1) || (article > 5) ) {
                TextReader.invalidInput(mTTS, tv);
            } else {
                getArticleDetails();
                goToReadArticleActivity(context, source, link, title, category, subcategory);
            }
        } else {
            if (setNum == setMax) {
                if ( (article < 1) || (article > lastSetSize) ) {
                    TextReader.invalidInput(mTTS, tv);
                } else {
                    getArticleDetails();
                    goToReadArticleActivity(context, source, link, title, category, subcategory);
                }
            } else {
                if ( (article < 1) || (article > 5) ) {
                    TextReader.invalidInput(mTTS, tv);
                } else {
                    getArticleDetails();
                    goToReadArticleActivity(context, source, link, title, category, subcategory);
                }
            }
        }
    }

    private void getArticleDetails() {
        int temp = article + (setNumIndex * 5) - 1;
        link = links.get(temp);
        title = titles.get(temp);
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

                case INQUIRER:
                    inquirerScraper = new InquirerScraper(category, subcategory);

                    links = inquirerScraper.links;
                    titles = inquirerScraper.titles;
                    Log.e("titleSets size", "before asigning titlesets");

                    titleSets = inquirerScraper.titleSets;


                    titlesNum = titles.size();
                    lastSetSize = titlesNum % 5;
                    setMax = inquirerScraper.titleSets.size();

                    break;
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
            mTTS.stop();
            switch (source) {
                case ABS:
                    tempy = getText(R.string.choose_article) + absScraper.titleSets.get(0);
                    tv.setText(tempy);
                    TextReader.say(mTTS, tv);
                    break;

                case INQUIRER:
                    tempy = getText(R.string.choose_article) + inquirerScraper.titleSets.get(0);
                    tv.setText(tempy);
                    TextReader.say(mTTS, tv);
                    break;
            }
        }
    }

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTTS.stop();
    }

}