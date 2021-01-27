package com.abc.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Vector;

import static com.abc.prototype.Navigate.goToArticleSelectionActivity;
import static com.abc.prototype.Navigate.goToSourceSelection;


//commit before changing app name

public class ReadArticleActivity extends AppCompatActivity {

    private String source;
    private String title;
    private String link;
    private String category;
    private String subcategory;

    private Vector<String> article;

    private final String ABS = "abs";
    private final String CNN = "cnn";
    private final String INQUIRER = "inquirer";
    private final String PHILSTAR = "philstar";

    private TextView tv;
    private TextToSpeech mTTS;

    private Button btnPrev;
    private Button btnNext;

    private boolean flag = false;


    private AbsScraper absScraper;
    private InquirerScraper inquirerScraper;
    private CnnScraper cnnScraper;

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

        btnPrev = findViewById(R.id.buttonReadArticlePreviousArticle);
        btnPrev.setAlpha((float) 0.5);
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousParagraph();

            }
        });


        btnNext = findViewById(R.id.buttonReadArticleNextArticle);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextParagraph();

            }
        });

        Button btnReadAgain = findViewById(R.id.button3);
        btnReadAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnNext.setAlpha(1);
                btnPrev.setAlpha((float) 0.5);
                index = 0;
                String temp = article.get(index) + getText(R.string.end_paragraph);
                tv.setText(temp);
                mTTS.stop();
                TextReader.say(mTTS, tv);
            }
        });

        Button btnNewArticle = findViewById(R.id.button4);
        btnNewArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTTS.stop();
                Context context = getApplicationContext();
                goToSourceSelection(context);
            }
        });

        Button btnBookmarkArticle = findViewById(R.id.buttonBookmarkArticle);
        btnBookmarkArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTTS.stop();
                if (!flag) {
                    flag = true;
                    Context context = getApplicationContext();
                    Bookmark bookmark = new Bookmark(context, title, article);
                    mTTS.speak("This article has been added to list of bookmarks.", TextToSpeech.QUEUE_FLUSH, null);
                }  else {
                    mTTS.speak("This article is already in list of bookmarks.", TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });

        Button btnBack = findViewById(R.id.buttonReadArticletArticleBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTTS.stop();
                Context context = getApplicationContext();
                goToArticleSelectionActivity(context, source, category, subcategory);
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
            String temp = article.get(index) + getText(R.string.end_paragraph);
            tv.setText(temp);
            mTTS.stop();
            TextReader.say(mTTS, tv);
        }
    }


    private void nextParagraph() {
        if (index == maxIndex - 1) {
            btnNext.setAlpha((float) 0.5);
            index++;
            String temp = article.get(index) + getText(R.string.end_article);
            tv.setText(temp);
            mTTS.stop();
            TextReader.say(mTTS, tv);
        }
        if (index == 0) {
            btnPrev.setAlpha((float) 1);
        }
        if (index < maxIndex) {
            index++;
            String temp = article.get(index) + getText(R.string.end_paragraph);
            tv.setText(temp);
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

                case INQUIRER:
                    inquirerScraper = new InquirerScraper(category, subcategory, link);
                    article = inquirerScraper.articleVector;
                    maxIndex = article.size() - 1;
                    break;

                case CNN:
                    cnnScraper = new CnnScraper(link, category);
                    article = cnnScraper.article;
                    maxIndex = article.size() - 1;
                    break;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            switch (source) {
                case ABS:
                    String temp = article.get(index) + getText(R.string.end_paragraph);
                    tv.setText(temp);
                    TextReader.say(mTTS, tv);
                    break;

                case INQUIRER:
                    String tempy = article.get(index) + getText(R.string.end_paragraph);
                    tv.setText(tempy);
                    TextReader.say(mTTS, tv);
                    break;

                case CNN:
                    String tempyy = article.get(index) + getText(R.string.end_paragraph);
                    tv.setText(tempyy);
                    TextReader.say(mTTS, tv);
                    break;

            }
        }
    }

    // TODO: 11/01/2021 paano kapag gusto ni user tuloy tuloy lang ang pagbasa ng article

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Context context = getApplicationContext();
        mTTS = TextReader.initialize(context);
//        TextReader.say(mTTS, tv);
    }

    @Override
    protected void onStop() {
        mTTS.stop();
        mTTS.shutdown();
        super.onStop();
    }

    @Override
    protected void onPause() {
        mTTS.stop();
        mTTS.shutdown();
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        mTTS.stop();
        mTTS.shutdown();
        super.onDestroy();


    }
}