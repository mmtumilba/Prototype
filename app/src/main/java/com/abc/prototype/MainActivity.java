package com.abc.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private TextToSpeech mTTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.opening);  //connect textview from layout

        Context context = getApplicationContext();
        mTTS = TextReader.initialize(context);
        TextReader.say(mTTS, tv);

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