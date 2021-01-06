package com.abc.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class CategorySelectionActivity extends AppCompatActivity {

    private TextView tv;
    private EditText et;
    private TextToSpeech mTTS;
//    private String source;

    private final String ABS = "abs";
    private final String GMA = "gma";
    private final String INQUIRER = "inquirer";
    private final String PHILSTAR = "philstar";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selection);

        Log.e("CHECKPOINT", "CategorySelectionActivity");


        Context context = getApplicationContext();
        mTTS = TextReader.initialize(context);
        TextReader.say(mTTS, tv);

        tv = findViewById(R.id.textViewChooseCategory);

        Bundle extras = getIntent().getExtras();

        //ito yung dati

//        String source = extras.getString("source");     // TODO: 05/01/2021 this may produce null pointerException
//        tv = findViewById(R.id.textViewTemp);
//        tv.setText(source);


//        Intent intent = getIntent();
//        String source = intent.getStringExtra("source");     // TODO: 05/01/2021 this may produce null pointerException
//        tv.setText(source);

    }
}



