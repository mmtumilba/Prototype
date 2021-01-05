package com.abc.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import static com.abc.prototype.Navigate.goToCategorySelectionActivity;

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
                goToSource(et);
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
                output = "";
                break;

            case 3:
                output = "";
                break;

            case 4:
                output = "";
                break;
        }

        return output;
    }

    private void goToSource(EditText et) {
        Context context = getApplicationContext();

        String temp = et.getText().toString();
        int source = Integer.parseInt(temp);

        if ( (source < 1) || (source >5) ){
            // add error tv
            // set error tv visible = true
            // read error tv
        } else {
            String sourceStr = getSourceString(source);
            goToCategorySelectionActivity(context, sourceStr);
        }

    }

}