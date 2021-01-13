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

import java.util.Vector;

import static com.abc.prototype.Navigate.goToChooseActionActivity;
import static com.abc.prototype.Navigate.goToReadBookmarkActivity;

public class BookmarksActivity extends AppCompatActivity {

    private Vector<String> titles;
    private Vector<String> titleSets;

    private int setNum = 1;
    private int setNumIndex;
    private int setMax;

    private int titlesNum;
    private int lastSetSize;
    private int article;
    private String articleIndex;


    private TextView tv;
    private EditText et;
    private TextToSpeech mTTS;

    private Button btnBack;
    private Button btnNext;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);

        tv = findViewById(R.id.textViewBookmarks);
        et = findViewById(R.id.editTextNumberBookmarksInput);
        new BookmarksThread().execute();

        final Context context = getApplicationContext();
        mTTS = TextReader.initialize(context);

        btnBack = findViewById(R.id.buttonBookmarksBack);
        btnBack.setAlpha((float) 0.5);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButton();
            }
        });

        btnNext = findViewById(R.id.buttonBookmarksNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextButton();
            }
        });

        btnSubmit = findViewById(R.id.buttonBookmarkSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitButton(context);
                Log.e("articleIndexVal", articleIndex);
            }
        });

    }

    private void backButton() {
        if (setMax == 1) {
            btnBack.setAlpha((float) 0.5);
            btnNext.setAlpha((float) 0.5);
        } else {
            if (setNum == 2) {
                btnBack.setAlpha((float) 0.5);
            } else if (setNum == setMax) {
                btnNext.setAlpha((float) 1);
            }
            if (setNum > 1) {
                setNum--;
                setNumIndex = setNum - 1;
                tv.setText(titleSets.get(setNumIndex));
                mTTS.stop();
                TextReader.say(mTTS, tv);
            }
        }
    }

    private void nextButton () {
        if (setMax == 1) {
            btnBack.setAlpha((float) 0.5);
            btnNext.setAlpha((float) 0.5);
        } else {
            int temp = setMax - 1;
            if (setNum == (temp)) {
                btnNext.setAlpha((float) 0.5);
            } else if (setNum == 1) {
                btnBack.setAlpha((float) 1.0);
            }
            if (setNum < setMax) {
                setNum++;
                setNumIndex = setNum - 1;
                tv.setText(titleSets.get(setNumIndex));
                mTTS.stop();
                TextReader.say(mTTS, tv);
            }
        }
    }

    private void submitButton(Context context) {
        if (et.length() == 0) {
            TextReader.invalidInput(mTTS, tv);
            return;
        }

        String temp = et.getText().toString();
        article = Integer.parseInt(temp);
        int val = article + (setNumIndex * 5) - 1;
        articleIndex = Integer.toString(val);


        if (lastSetSize == 0) {
            if ( (article < 1) || (article > 5) ) {
                TextReader.invalidInput(mTTS, tv);
            } else {
                goToReadBookmarkActivity(context, articleIndex);

            }
        } else {
            if (setNum == setMax) {
                if ( (article < 1) || (article > lastSetSize) ) {
                    TextReader.invalidInput(mTTS, tv);
                } else {
                    goToReadBookmarkActivity(context, articleIndex);
                }
            } else {
                if ( (article < 1) || (article > 5) ) {
                    TextReader.invalidInput(mTTS, tv);
                } else {
                    goToReadBookmarkActivity(context, articleIndex);
                }
            }
        }
        mTTS.stop();

    }

    // TODO: 13/01/2021 may bug sa pag opaque ng back button kapag 2 lang ang titlesets

    private class BookmarksThread extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            Context context = getApplicationContext();
            Bookmark bookmark = new Bookmark(context);
            titles = bookmark.titles;
            titleSets = bookmark.titleSets;

            titlesNum = titles.size();
            lastSetSize = titlesNum % 5;
            setMax = titleSets.size();

            if (setMax == 1 ){
                btnNext.setAlpha((float) 0.5);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            tv.setText(titleSets.get(0));
        }
    }
}