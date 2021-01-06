package com.abc.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import static com.abc.prototype.Navigate.goToArticleSelectionActivity;

public class CategorySelectionActivity extends AppCompatActivity {

    private TextView tv;
    private EditText et;
    private TextToSpeech mTTS;

    private Button btnBack;
    private Button btnNext;
    private Button btnSubmit;


    private String source;
    private String category;
    private String subcategory = "";

    private boolean hasSubcategory = false;

    private final String ABS = "abs";
    private final String GMA = "gma";
    private final String INQUIRER = "inquirer";
    private final String PHILSTAR = "philstar";

    private int setNum = 1;
    private int setMax, categoryInt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selection);

        Context context = getApplicationContext();
        mTTS = TextReader.initialize(context);
        tv = findViewById(R.id.textViewChooseCategory);
        et = findViewById(R.id.editTextNumberInputCategory);

        Bundle extras = getIntent().getExtras();
        source = extras.getString("source");     // TODO: 05/01/2021 this may produce null pointerException -> di na kasi sa prev activity, sinigurado nang hindi makakalusot pag walang laman
        setTv();

        // TODO: 07/01/2021 stop reader upon action (next/back/submit)
        btnBack = findViewById(R.id.buttonCategorySelectionBack);
        btnBack.setAlpha((float) 0.5);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (source) {
                    case ABS:
                        absBackButton();
                        break;
                    case GMA:
                        break;

                    case INQUIRER:
                        break;

                    case PHILSTAR:
                        break;

                }
            }
        });

        btnNext = findViewById(R.id.buttonCategorySelectionNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (source) {
                    case ABS:
                        absNextButton();
                        break;
                    case GMA:
                        break;

                    case INQUIRER:
                        break;

                    case PHILSTAR:
                        break;

                }
            }
        });


        btnSubmit = findViewById(R.id.buttonCategorySelectionSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et.length() == 0) {
                    TextReader.invalidInput(mTTS, tv);
                    return;
                }
                String temp = et.getText().toString();
                categoryInt = Integer.parseInt(temp);
                switch (source) {
                    case ABS:
                        absSubmitButton();
                        break;
                    case GMA:
                        break;

                    case INQUIRER:
                        break;

                    case PHILSTAR:
                        break;

                }
            }
        });

    }

    private void absBackButton () {
            setNum--;
            btnBack.setAlpha((float) 0.5);
            btnNext.setAlpha((float) 1);

            tv.setText(R.string.abs_categories1);
    }

    private void absNextButton() {
            setNum++;
            btnBack.setAlpha((float) 1);
            btnNext.setAlpha((float) 0.5);

            tv.setText(R.string.abs_categories2);
    }

    private void absSubmitButton() {
        Context context = getApplicationContext();

        //error checking pag walang laman ang et


        if ( (categoryInt < 1)|| (categoryInt > 5) ) {
            TextReader.invalidInput(mTTS, tv);
        } else {
            category = getAbsCategoryString();
            goToArticleSelectionActivity(context, source, category, subcategory);
        }
    }

    private String getAbsCategoryString() {
        String output = "";

        switch (setNum) {
            case 1:
                switch (categoryInt) {
                    case 1:
                        output = "news";
                        break;
                    case 2:
                        output = "business";
                        break;
                    case 3:
                        output = "entertainment";
                        break;
                    case 4:
                        output = "life";
                        break;
                    case 5:
                        output = "sports";
                        break;
                }
                break;

            case 2:
                switch (categoryInt) {
                    case 1:
                        output = "overseas";
                        break;
                    case 2:
                        output = "spotlight";
                        break;
                }
        }
        return output;
    }

        private void setTv () {

            switch (source) {
                case ABS:
                    tv.setText(R.string.abs_categories1);
                    setMax = 2;
                    break;

                case GMA:
                    hasSubcategory = true;
                    tv.setText(R.string.gma_categories1);
                    setMax = 3;
                    break;

                case INQUIRER:
                    hasSubcategory = true;
                    tv.setText(R.string.inquirer_categories1);
                    setMax = 2;
                    break;

                case PHILSTAR:
                    tv.setText(R.string.philstar_categories1);
                    setMax = 2;
                    break;

            }
        }


}



