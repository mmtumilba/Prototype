package com.abc.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import static com.abc.prototype.Navigate.goToBookmarksActivity;
import static com.abc.prototype.Navigate.goToCategorySelectionActivity;
import static com.abc.prototype.Navigate.goToSpeedActivity;

public class SourceSelectionActivity extends AppCompatActivity  {

    private TextView tv;
    private TextToSpeech mTTS;

    int source = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source_selection);

        tv = findViewById(R.id.textViewChooseSource);

        Context context = getApplicationContext();
        mTTS = TextReader.initialize(context);
        TextReader.say(mTTS, tv);

        Button btn1 = findViewById(R.id.button1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                source = 1;
                mTTS.stop();
                goToSource();
            }
        });

        Button btn2 = findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                source = 2;
//                mTTS.stop();
//                goToSource();
                source = 3;
                mTTS.stop();
                goToSource();
            }
        });

//        Button btn3 = findViewById(R.id.button3);
//        btn3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                source = 3;
//                mTTS.stop();
//                goToSource();
//            }
//        });
//
//
//        Button btn4 = findViewById(R.id.button4);
//        btn4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                source = 4;
//                mTTS.stop();
//                goToSource();
//            }
//        });


        Log.e("CHECKPOINT", "ONcREATE");

        Button btnSpeed = findViewById(R.id.buttonSourceSelectionSpeed);
        btnSpeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTTS.stop();
                Context context = getApplicationContext();
                goToSpeedActivity(context);
            }
        });


        Button btnBookmarks = findViewById(R.id.buttonSourceSelectionBookmarks);
        btnBookmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTTS.stop();
                Context context = getApplicationContext();
                goToBookmarksActivity(context);
            }
        });

    }

    private String getSourceString(int source) {
        String output = "";
        switch (source) {
            case 1:
                output = "abs";
                break;
            case 2:
                output = "gma";
                break;

            case 3:
                output = "inquirer";
                break;

            case 4:
                output = "philstar";
                break;
        }

        Log.e("CHECKPOINT", "getSourceString");
        return output;
    }

    private void goToSource() {
        Context sourceContext = getApplicationContext();

        if (source == 0) {
            String temp = "There are only 2 choices. Try again. " + tv.getText().toString();
            TextReader.sayText(mTTS, temp);
            return;
        }

        if ( (source < 1) || (source > 4)){
            String temp = "There are only 2 choices. Try again. " + tv.getText().toString();
            TextReader.sayText(mTTS, temp); // TODO: 25/01/2021 change to proper number of sources
        } else {
            String sourceStr = getSourceString(source);
            goToCategorySelectionActivity(sourceContext, sourceStr);
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
}
