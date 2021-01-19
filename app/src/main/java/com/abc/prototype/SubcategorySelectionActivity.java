package com.abc.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import static com.abc.prototype.Navigate.goToArticleSelectionActivity;

public class SubcategorySelectionActivity extends AppCompatActivity {

    private TextView tv;
    private EditText et;
    private TextToSpeech mTTS;

    private Button btnBack;
    private Button btnNext;
    private Button btnSubmit;

    private String source;
    private String category;
    private String subcategory;

    private final String NEWS = "news";
    private final String OPINION = "opinion";
    private final String SPORTS = "sports";
    private final String LIFESTYLE = "lifestyle";
    private final String ENTERTAINMENT = "entertainment";
    private final String BUSINESS = "business";
    private final String TECHNOLOGY = "technology";
    private final String GLOBAL_NATION = "global_nation";

    private int setNum = 1;
    private int setMax, subcategoryInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategory_selection);

        Context context = getApplicationContext();
        mTTS = TextReader.initialize(context);
        tv = findViewById(R.id.textViewChooseSubcategory);
        et = findViewById(R.id.editTextNumberInputSubategory);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            source = extras.getString("source");     // TODO: 05/01/2021 this may produce null pointerException -> di na kasi sa prev activity, sinigurado nang hindi makakalusot pag walang laman
            category = extras.getString("category");     // TODO: 05/01/2021 this may produce null pointerException -> di na kasi sa prev activity, sinigurado nang hindi makakalusot pag walang laman
        }

        btnBack = findViewById(R.id.buttonSubcategorySelectionBack);
        btnNext = findViewById(R.id.buttonSubcategorySelectionNext);
        btnSubmit = findViewById(R.id.buttonSubcategorySelectionSubmit);

        setTvAndOpacity();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTTS.stop();
                switch (category) {
                    case SPORTS:
                        while (setNum == 2) {
                            setNum--;
                            btnBack.setAlpha((float) 0.5);
                            btnNext.setAlpha((float) 1);
                            tv.setText(R.string.inquirer_sports_subcategories1);
                        }
                        break;
                    case BUSINESS:
                        while (setNum == 2) {
                            setNum--;
                            btnBack.setAlpha((float) 0.5);
                            btnNext.setAlpha((float) 1);
                            tv.setText(R.string.inquirer_business_subcategories1);
                        }
                        break;
                    case GLOBAL_NATION:
                        while (setNum == 2) {
                            setNum--;
                            btnBack.setAlpha((float) 0.5);
                            btnNext.setAlpha((float) 1);
                            tv.setText(R.string.inquirer_global_nation_subcategories1);
                        }
                        break;

                }
                TextReader.say(mTTS, tv);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTTS.stop();
                switch (category) {
                    case SPORTS:
                        while (setNum == 1) {
                            setNum++;
                            btnBack.setAlpha((float) 1);
                            btnNext.setAlpha((float) 0.5);
                            tv.setText(R.string.inquirer_sports_subcategories2);
                        }
                        break;
                    case BUSINESS:
                        while (setNum == 1) {
                            setNum++;
                            btnBack.setAlpha((float) 1);
                            btnNext.setAlpha((float) 0.5);
                            tv.setText(R.string.inquirer_business_subcategories2);
                        }
                        break;
                    case GLOBAL_NATION:
                        while (setNum == 1) {
                            setNum++;
                            btnBack.setAlpha((float) 1);
                            btnNext.setAlpha((float) 0.5);
                            tv.setText(R.string.inquirer_global_nation_subcategories2);
                        }
                        break;

                }
                TextReader.say(mTTS, tv);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTTS.stop();
                if (et.length() == 0) {
                    TextReader.invalidInput(mTTS, tv);
                    return;
                }
                String temp = et.getText().toString();
                subcategoryInt = Integer.parseInt(temp);
                Context context = getApplicationContext();
                switch (category) {
                    case NEWS:
                    case OPINION:
                    case LIFESTYLE:
                    case ENTERTAINMENT:
                        if ( (subcategoryInt < 1)|| (subcategoryInt > 5) ) {
                            TextReader.invalidInput(mTTS, tv);
                        } else {
                            subcategory = getSubcategoryString(subcategoryInt);
                            goToArticleSelectionActivity(context, source, category, subcategory);
                        }
                        break;

                    case SPORTS:
                        switch (setNum) {
                            case 1:
                                if ( (subcategoryInt < 1)|| (subcategoryInt > 5) ) {
                                    TextReader.invalidInput(mTTS, tv);
                                } else {
                                    subcategory = getSubcategoryString(subcategoryInt);
                                    goToArticleSelectionActivity(context, source, category, subcategory);
                                }
                                break;
                            case 2:
                                if ( (subcategoryInt < 1)|| (subcategoryInt > 4) ) {
                                    TextReader.invalidInput(mTTS, tv);
                                } else {
                                    subcategory = getSubcategoryString(subcategoryInt);
                                    goToArticleSelectionActivity(context, source, category, subcategory);
                                }
                                break;
                        }
                        break;


                    case BUSINESS:
                        switch (setNum) {
                            case 1:
                                if ( (subcategoryInt < 1)|| (subcategoryInt > 5) ) {
                                    TextReader.invalidInput(mTTS, tv);
                                } else {
                                    subcategory = getSubcategoryString(subcategoryInt);
                                    goToArticleSelectionActivity(context, source, category, subcategory);
                                }
                                break;
                            case 2:
                                if ( (subcategoryInt < 1)|| (subcategoryInt > 2) ) {
                                    TextReader.invalidInput(mTTS, tv);
                                } else {
                                    subcategory = getSubcategoryString(subcategoryInt);
                                    goToArticleSelectionActivity(context, source, category, subcategory);
                                }
                                break;
                        }
                        break;

                    case TECHNOLOGY:
                        if ( (subcategoryInt < 1)|| (subcategoryInt > 2) ) {
                            TextReader.invalidInput(mTTS, tv);
                        } else {
                            subcategory = getSubcategoryString(subcategoryInt);
                            goToArticleSelectionActivity(context, source, category, subcategory);
                        }
                        break;

                    case GLOBAL_NATION:
                        switch (setNum) {
                            case 1:
                                if ( (subcategoryInt < 1)|| (subcategoryInt > 5) ) {
                                    TextReader.invalidInput(mTTS, tv);
                                } else {
                                    subcategory = getSubcategoryString(subcategoryInt);
                                    goToArticleSelectionActivity(context, source, category, subcategory);
                                }
                                break;
                            case 2:
                                if ( (subcategoryInt < 1)|| (subcategoryInt > 3) ) {
                                    TextReader.invalidInput(mTTS, tv);
                                } else {
                                    subcategory = getSubcategoryString(subcategoryInt);
                                    goToArticleSelectionActivity(context, source, category, subcategory);
                                }
                                break;
                        }
                        break;
                }
            }

        });


    }

    private String getSubcategoryString(int subcategoryInt) {
        String output = "";
        switch (category) {
            case NEWS:
                switch (subcategoryInt) {
                    case 1:
                        output = "headlines";
                        break;
                    case 2:
                        output = "metro";
                        break;
                    case 3:
                        output = "region";
                        break;
                    case 4:
                        output = "nation";
                        break;
                    case 5:
                        output = "world";
                        break;
                }
                break;

            case OPINION:
                switch (subcategoryInt) {
                    case 1:
                        output = "editorial";
                        break;
                    case 2:
                        output = "columns";
                        break;
                    case 3:
                        output = "viewpoints";
                        break;
                    case 4:
                        output = "talk_of_the_town";
                        break;
                    case 5:
                        output = "lovelife";
                        break;
                }
                break;

            case LIFESTYLE:
                switch (subcategoryInt) {
                    case 1:
                        output = "fashion";
                        break;
                    case 2:
                        output = "beauty";
                        break;
                    case 3:
                        output = "culture";
                        break;
                    case 4:
                        output = "events";
                        break;
                    case 5:
                        output = "human_interest";
                        break;
                }
                break;

            case ENTERTAINMENT:
                switch (subcategoryInt) {
                    case 1:
                        output = "headlines";
                        break;
                    case 2:
                        output = "latest_entertainment_stories";
                        break;
                    case 3:
                        output = "columns";
                        break;
                    case 4:
                        output = "movies";
                        break;
                    case 5:
                        output = "indie_films";
                        break;
                }
                break;

            case SPORTS:
                switch (setNum) {
                    case 1:
                        switch (subcategoryInt) {
                            case 1:
                                output = "basketball";
                                break;
                            case 2:
                                output = "boxing";
                                break;
                            case 3:
                                output = "volleyball";
                                break;
                            case 4:
                                output = "football";
                                break;
                            case 5:
                                output = "other_sports";
                                break;
                        }
                        break;
                    case 2:
                        switch (subcategoryInt) {
                            case 1:
                                output = "pba";
                                break;
                            case 2:
                                output = "uaap";
                                break;
                            case 3:
                                output = "ncaa";
                                break;
                            case 4:
                                output = "one_championship";
                                break;
                        }
                        break;
                }
                break;

            case BUSINESS:
                switch (setNum) {
                    case 1:
                        switch (subcategoryInt) {
                            case 1:
                                output = "mobility"; // TODO: 19/01/2021 remove from list
                                break;
                            case 2:
                                output = "job_market"; // TODO: 19/01/2021 remove from list
                                break;
                            case 3:
                                output = "stock_exchange";
                                break;
                            case 4:
                                output = "property_guide";
                                break;
                            case 5:
                                output = "columns";
                                break;
                        }
                        break;

                    case 2:
                        switch (subcategoryInt) {
                            case 1:
                                output = "stock_market_quarterly";
                                break;
                            case 2:
                                output = "classifieds";
                                break;
                        }
                }
                break;

            case TECHNOLOGY:
                switch (subcategoryInt) {
                    case 1:
                        output = "headlines";
                        break;
                    case 2:
                        output = "latest_technology_stories";
                        break;
                }
                break;

            case GLOBAL_NATION:
                switch (setNum) {
                    case 1:
                        switch (subcategoryInt) {
                            case 1:
                                output = "philippines";
                                break;
                            case 2:
                                output = "asia_and_pacific";
                                break;
                            case 3:
                                output = "americas";
                                break;
                            case 4:
                                output = "middle_east_and_africa";
                                break;
                            case 5:
                                output = "europe";
                                break;
                        }
                        break;
                    case 2:
                        switch (subcategoryInt) {
                            case 1:
                                output = "global";
                                break;
                            case 2:
                                output = "pinoy_columns";
                                break;
                            case 3:
                                output = "events";
                                break;
                        }
                }
                break;
        }
        return output;
    }

    private void setTvAndOpacity () {
        switch (category) {
            case NEWS:
                tv.setText(R.string.inquirer_news_subcategories1);
                btnBack.setAlpha((float) 0.5);
                btnNext.setAlpha((float) 0.5);
                setMax = 1;
                break;

            case OPINION:
                tv.setText(R.string.inquirer_opinion_subcategories1);
                btnBack.setAlpha((float) 0.5);
                btnNext.setAlpha((float) 0.5);
                setMax = 1;
                break;

            case SPORTS:
                tv.setText(R.string.inquirer_sports_subcategories1);
                btnBack.setAlpha((float) 0.5);
                setMax = 2;
                break;

            case LIFESTYLE:
                tv.setText(R.string.inquirer_lifestyle_subcategories1);
                btnBack.setAlpha((float) 0.5);
                btnNext.setAlpha((float) 0.5);
                setMax = 1;
                break;

            case ENTERTAINMENT:
                tv.setText(R.string.inquirer_entertainment_subcategories1);
                btnBack.setAlpha((float) 0.5);
                btnNext.setAlpha((float) 0.5);
                setMax = 1;
                break;

            case BUSINESS:
                tv.setText(R.string.inquirer_business_subcategories1);
                btnBack.setAlpha((float) 0.5);
                setMax = 2;
                break;

            case TECHNOLOGY:
                tv.setText(R.string.inquirer_technology_subcategories1);
                btnBack.setAlpha((float) 0.5);
                btnNext.setAlpha((float) 0.5);
                setMax = 1;
                break;

            case GLOBAL_NATION:
                tv.setText(R.string.inquirer_global_nation_subcategories1);
                btnBack.setAlpha((float) 0.5);
                setMax = 2;
                break;
        }
    }
}