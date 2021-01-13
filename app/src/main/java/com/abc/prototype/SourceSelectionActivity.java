package com.abc.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import static com.abc.prototype.Navigate.goToBookmarksActivity;
import static com.abc.prototype.Navigate.goToCategorySelectionActivity;
import static com.abc.prototype.Navigate.goToSpeedActivity;

public class SourceSelectionActivity extends AppCompatActivity  {

    private TextView tv;
    private EditText et;
    private TextToSpeech mTTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source_selection);

        tv = findViewById(R.id.textViewChooseSource);
        et = findViewById(R.id.editTextNumberInputSource);


        Context context = getApplicationContext();
        mTTS = TextReader.initialize(context);
        TextReader.say(mTTS, tv);

        Button btnSubmit = findViewById(R.id.buttonSourceSelectionSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTTS.stop();
                goToSource(et);
            }
        });

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

//        buttonSourceSelectionBookmarks
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

    private void goToSource(EditText et) {
        Context sourceContext = getApplicationContext();

        if (et.length() == 0) {
            TextReader.invalidInput(mTTS, tv);
            return;
        }

        String temp = et.getText().toString();
        int source = Integer.parseInt(temp);
        if ( (source < 1) || (source > 5)){
            TextReader.invalidInput(mTTS, tv);
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
}
