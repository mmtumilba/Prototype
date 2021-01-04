package com.abc.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.abc.prototype.Navigate;

import static com.abc.prototype.Navigate.goToAbsCategorySelection;

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

    private void goToSource(EditText et) {
        Context context = getApplicationContext();

        String temp = et.getText().toString();
        int source = Integer.parseInt(temp);

        switch (source) {
            case 1:
                goToAbsCategorySelection(context);
                break;

            case 2:
                break;

            case 3:
                break;

            case 4:
                break;

            default:
                // add error tv
                // set error tv visible = true
                // read error tv
        }

    }

}