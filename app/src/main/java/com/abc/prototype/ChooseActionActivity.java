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

import static com.abc.prototype.Navigate.goToReadArticleActivity;
import static com.abc.prototype.Navigate.goToSourceSelection;

// // TODO: 09/01/2021 needs more work :)) 
public class ChooseActionActivity extends AppCompatActivity {

    private String source;
    private String title;
    private String link;

    private TextView tv;
    private TextToSpeech mTTS;

    private Button btnRead;
    private Button btnBookmark;

    private final String ABS = "abs";
    private final String GMA = "gma";
    private final String INQUIRER = "inquirer";
    private final String PHILSTAR = "philstar";

    private Vector<String> article;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_action);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            source = extras.getString("source");
            link = extras.getString("link");
            title = extras.getString("title");

            Log.e("link", link);
            Log.e("title", title);
        }

        Context context = getApplicationContext();
        mTTS = TextReader.initialize(context);

        tv = findViewById(R.id.textViewChooseActionTitle);
        tv.setText(title);

        btnRead = findViewById(R.id.buttonChooseActionRead);
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                goToReadArticleActivity(context,source, link, title);
            }
        });
        
        btnBookmark = findViewById(R.id.buttonChooseActionBookmark);
        btnBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 09/01/2021 mechenism for bookmarking an article
                // ubra bookmark obj nga isulod sa xml file ang data
                // ubra sang article nga object (??)

                // scrape the article

                new BookmarkThread().execute();

                Context context = getApplicationContext();
                goToSourceSelection(context);
            }
        });
    }

    private class BookmarkThread extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            switch (source) {
                case ABS:
                    AbsScraper absScraper = new AbsScraper(source, link);
                    article = absScraper.article;

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

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Bookmark bookmark = new Bookmark(title, article);
            bookmark.addToXML();
        }
    }


}