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

import static com.abc.prototype.Navigate.goToBookmarksActivity;
import static com.abc.prototype.Navigate.goToSourceSelection;

public class ReadBookmarkActivity extends AppCompatActivity {

    private Vector<String> article;
    private int articleIndex;
    private int index = 0;
    private int maxIndex;

    private TextView tv;
    private TextToSpeech mTTS;

    private Button btnPrev;
    private Button btnNext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_bookmark);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String temp  = extras.getString("articleIndex");
            articleIndex = Integer.parseInt(temp);

            new BookmarkThread().execute();
        }

        Context context = getApplicationContext();
        mTTS = TextReader.initialize(context);

        tv = findViewById(R.id.textViewReadBookmark);

        btnPrev = findViewById(R.id.buttonReadBookmarkPrevious);
        btnPrev.setAlpha((float) 0.5);
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousParagraph();
            }
        });

        btnNext = findViewById(R.id.buttonReadBookmarkNext);
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
                Context context = getApplicationContext();
                goToSourceSelection(context);
                mTTS.stop();
            }
        });

        Button btnBack = findViewById(R.id.buttonReadBookmarkBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTTS.stop();
                Context context = getApplicationContext();
                goToBookmarksActivity(context);
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


    private class BookmarkThread extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            Context context = getApplicationContext();
            Bookmark bookmark = new Bookmark(context, articleIndex);
            article = bookmark.article;
            maxIndex = article.size() - 1;

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            String temp = article.get(index) + getText(R.string.end_paragraph);
            tv.setText(temp);
        }
    }

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