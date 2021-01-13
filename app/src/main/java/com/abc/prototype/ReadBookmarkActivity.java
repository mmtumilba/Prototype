package com.abc.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.print.PrinterId;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Vector;

public class ReadBookmarkActivity extends AppCompatActivity {

    private Vector<String> article;
    private int articleIndex;
    private int index = 0;
    private int maxIndex;

    private TextView tv;
    private TextToSpeech mTTS;

    private Button btnPrev;
    private Button btnPausePlay;
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

//        btnPausePlay = findViewById(R.id.buttonReadArticlePausePlay);
//        btnPausePlay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        btnNext = findViewById(R.id.buttonReadBookmarkNext);
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
            tv.setText(article.get(index));
        }
    }
}