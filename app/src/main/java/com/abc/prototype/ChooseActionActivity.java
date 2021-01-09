package com.abc.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

                Context context = getApplicationContext();
                goToSourceSelection(context);
            }
        });



    }
}