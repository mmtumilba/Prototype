package com.abc.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private TextToSpeech mTTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.opening);  //connect textview from layout

        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {   //initialize textToSpeech
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTS.setLanguage(Locale.CANADA);
                    TextReader.say(mTTS, tv);
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });


        new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                Context context = getApplicationContext();
                Navigate.goToSourceSelection(context);
            }
        }.start();

    }
}