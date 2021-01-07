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
import static com.abc.prototype.Navigate.goToSubcategorySelectionActivity;

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
                        gmaBackButton();
                        break;

                    case INQUIRER:
                        inquirerBackButton();
                        break;

                    case PHILSTAR:
                        philstarBackButton();
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
                        gmaNextButton();
                        break;

                    case INQUIRER:
                        inquirerNextButton();
                        break;

                    case PHILSTAR:
                        philstarNextButton();
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
                        gmaSubmitButton();
                        break;

                    case INQUIRER:
                        inquirerSubmitButton();
                        break;

                    case PHILSTAR:
                        philstarSubmitButton();
                        break;

                }
            }
        });

        //// TODO: 07/01/2021 gma and inquirer should go to subcategoryselection instead of articleselection
    }

    private void backButton () {
        while (setNum == 2) {
            setNum--;
            btnBack.setAlpha((float) 0.5);
            btnNext.setAlpha((float) 1);
        }
    }

    private void nextButton () {
        while (setNum == 1) {
            setNum++;
            btnBack.setAlpha((float) 1);
            btnNext.setAlpha((float) 0.5);
        }
    }


    private void absBackButton () {
        backButton();
        tv.setText(R.string.abs_categories1);
    }

    private void absNextButton () {
            nextButton();
            tv.setText(R.string.abs_categories2);
    }

    private void absSubmitButton() {
        Context context = getApplicationContext();
        switch (setNum) {
            case 1:
                if ( (categoryInt < 1)|| (categoryInt > 5) ) {
                    TextReader.invalidInput(mTTS, tv);
                } else {
                    category = getAbsCategoryString();
                    goToArticleSelectionActivity(context, source, category, subcategory);
                }
                break;
            case 2:
                if ( (categoryInt < 1)|| (categoryInt > 2) ) {
                    TextReader.invalidInput(mTTS, tv);
                } else {
                    category = getAbsCategoryString();
                    goToArticleSelectionActivity(context, source, category, subcategory);
                }
                break;
        }
    }

    private String getAbsCategoryString () {
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


    private void gmaBackButton () {
        switch (setNum) {
            case 2: // -> 1
                setNum--;
                btnBack.setAlpha((float) 0.5);
                tv.setText(R.string.gma_categories1);
                break;

            case 3: // -> 2
                setNum--;
                btnNext.setAlpha((float) 1);
                tv.setText(R.string.gma_categories2);
                break;
        }
    }

    private void gmaNextButton () {
        switch (setNum) {
            case 1: // -> 2
                setNum++;
                btnBack.setAlpha((float) 1);
                tv.setText(R.string.gma_categories2);
                break;

            case 2: // -> 3
                setNum++;
                btnNext.setAlpha((float) 0.5);
                tv.setText(R.string.gma_categories3);
                break;
        }
    }

    private void gmaSubmitButton () {
        Context context = getApplicationContext();
        switch (setNum) {
            case 1:
            case 2:
                if ( (categoryInt < 1)|| (categoryInt > 5) ) {
                    TextReader.invalidInput(mTTS, tv);
                } else {
                    category = getGmaCategoryString();
                    goToSubcategorySelectionActivity(context, source, category);
                }
                break;

            case 3:
                if (categoryInt == 1) {
                    category = getGmaCategoryString();
                    goToSubcategorySelectionActivity(context, source, category);
                } else {
                    TextReader.invalidInput(mTTS, tv);
                }
        }
    }

    private String getGmaCategoryString () {
        String output = "";

        switch (setNum) {
            case 1:
                switch (categoryInt) {
                    case 1:
                        output = "news";
                        break;
                    case 2:
                        output = "money";
                        break;
                    case 3:
                        output = "sports";
                        break;
                    case 4:
                        output = "pinoy_abroad";
                        break;
                    case 5:
                        output = "sci_tech";
                        break;
                }
                break;

            case 2:
                switch (categoryInt) {
                    case 1:
                        output = "showbiz";
                        break;
                    case 2:
                        output = "lifesty;e";
                        break;
                    case 3:
                        output = "opinion";
                        break;
                    case 4:
                        output = "hashtag";
                        break;
                    case 5:
                        output = "serbisiyo_publiko";
                        break;
                }
                break;

            case 3:
                output = "community_bulletin_board";
                break;
        }
        return output;
    }



    private void inquirerBackButton () {
        backButton();
        tv.setText(R.string.inquirer_categories1);
    }

    private void inquirerNextButton () {
        nextButton();
        tv.setText(R.string.inquirer_categories2);
    }

    private void inquirerSubmitButton() {
        Context context = getApplicationContext();
        switch (setNum) {
            case 1:
                if ( (categoryInt < 1)|| (categoryInt > 5) ) {
                    TextReader.invalidInput(mTTS, tv);
                } else {
                    category = getInquirerCategoryString();
                    goToSubcategorySelectionActivity(context, source, category);
                }
                break;
            case 2:
                if ( (categoryInt < 1)|| (categoryInt > 3) ) {
                    TextReader.invalidInput(mTTS, tv);
                } else {
                    category = getInquirerCategoryString();
                    goToSubcategorySelectionActivity(context, source, category);
                }
                break;
        }

    }


    private String getInquirerCategoryString () {
        String output = "";

        switch (setNum) {
            case 1:
                switch (categoryInt) {
                    case 1:
                        output = "news";
                        break;
                    case 2:
                        output = "opinion";
                        break;
                    case 3:
                        output = "sports";
                        break;
                    case 4:
                        output = "lifestyle";
                        break;
                    case 5:
                        output = "entertainment";
                        break;
                }
                break;

            case 2:
                switch (categoryInt) {
                    case 1:
                        output = "business";
                        break;
                    case 2:
                        output = "technology";
                        break;
                    case 3:
                        output = "global_nation";
                        break;
                }
        }

        return output;
    }

    //// TODO: 07/01/2021 clear / reset variables kapag nag next activity
    //prbably sa back and next button check your code lalo sa increment at decrement


    private void philstarBackButton () {
        backButton();
        tv.setText(R.string.philstar_categories1);
    }

    private void philstarNextButton () {
        nextButton();
        tv.setText(R.string.philstar_categories2);
    }

    private void philstarSubmitButton (){
        Context context = getApplicationContext();
        switch (setNum) {
            case 1:
                if ( (categoryInt < 1)|| (categoryInt > 5) ) {
                    TextReader.invalidInput(mTTS, tv);
                } else {
                    category = getPhilstarCategoryString();
                    goToArticleSelectionActivity(context, source, category, subcategory);
                }
                break;
            case 2:
                if ( (categoryInt < 1)|| (categoryInt > 4) ) {
                    TextReader.invalidInput(mTTS, tv);
                } else {
                    category = getPhilstarCategoryString();
                    goToArticleSelectionActivity(context, source, category, subcategory);
                }
                break;
        }
    }

    private String getPhilstarCategoryString () {
        String output = "";

        switch (setNum) {
            case 1:
                switch (categoryInt) {
                    case 1:
                        output = "headlines";
                        break;
                    case 2:
                        output = "opinion";
                        break;
                    case 3:
                        output = "nation";
                        break;
                    case 4:
                        output = "world";
                        break;
                    case 5:
                        output = "business";
                        break;
                }
                break;

            case 2:
                switch (categoryInt) {
                    case 1:
                        output = "sports";
                        break;
                    case 2:
                        output = "entertainment";
                        break;
                    case 3:
                        output = "lifestyle";
                        break;
                    case 4:
                        output = "other_sections";
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



