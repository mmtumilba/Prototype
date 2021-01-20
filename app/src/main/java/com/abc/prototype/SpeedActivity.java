package com.abc.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SpeedActivity extends AppCompatActivity {

    private TextView tv;
    private TextToSpeech mTTS;

    private Button btnSlow;
    private Button btnNormal;
    private Button btnFast;

    // create a static variable for the speed

    // TODO: 09/01/2021 butangi onPostResume ang mga activities para di ka na mag implement ng sarili mong back button eheehehe

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed);

        Context context = getApplicationContext();
        mTTS = TextReader.initialize(context);

        tv =findViewById(R.id.textViewSpeed);

        btnSlow = findViewById(R.id.buttonSpeedSlow);
        btnSlow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextReader.speed = (float) 0.5;
                TextReader.say(mTTS, tv);
            }
        });

        btnNormal = findViewById(R.id.buttonSpeedNormal);
        btnNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextReader.speed = (float) 1;
                TextReader.say(mTTS, tv);
            }
        });

        btnFast = findViewById(R.id.buttonSpeedFast);
        btnFast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextReader.speed = (float) 1.5;
                TextReader.say(mTTS, tv);
            }
        });
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