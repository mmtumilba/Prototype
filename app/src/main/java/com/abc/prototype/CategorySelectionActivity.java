package com.abc.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.abc.prototype.Navigate.goToArticleSelectionActivity;
import static com.abc.prototype.Navigate.goToSourceSelection;
import static com.abc.prototype.Navigate.goToSubcategorySelectionActivity;

public class CategorySelectionActivity extends AppCompatActivity {

    private TextView tv;
    private TextToSpeech mTTS;

    private Button btnPrev;
    private Button btnNext;
    private Button btnBack;


    private String source;
    private String category;
    private String subcategory = "";

    private boolean hasSubcategory = false;

    private final String ABS = "abs";
    private final String GMA = "gma";
    private final String INQUIRER = "inquirer";
    private final String PHILSTAR = "philstar";

    private int setNum = 1;
    private int categoryInt = 0;
    private int setMax;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selection);

        Context context = getApplicationContext();
        mTTS = TextReader.initialize(context);
        tv = findViewById(R.id.textViewChooseCategory);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            source = extras.getString("source");     // TODO: 05/01/2021 this may produce null pointerException -> di na kasi sa prev activity, sinigurado nang hindi makakalusot pag walang laman
        }
        setTv();


        Button btn1 = findViewById(R.id.button1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryInt = 1;
                submit();
            }
        });

        Button btn2 = findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryInt = 2;
                submit();
            }
        });

        Button btn3 = findViewById(R.id.button3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryInt = 3;
                submit();
            }
        });


        Button btn4 = findViewById(R.id.button4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryInt = 4;
                submit();
            }
        });

        Button btn5 = findViewById(R.id.button5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryInt = 5;
                submit();
            }
        });


        btnPrev = findViewById(R.id.buttonCategorySelectionPrev);
        btnPrev.setAlpha((float) 0.5);
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (source) {
                    case ABS:
                        absPrevButton();
                        break;
                    case GMA:
                        gmaPrevButton();
                        break;

                    case INQUIRER:
                        inquirerPrevButton();
                        break;

                    case PHILSTAR:
                        philstarPrevButton();
                        break;

                }
                mTTS.stop();
                TextReader.say(mTTS, tv);

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
            mTTS.stop();
            TextReader.say(mTTS, tv);
            }
        });

        btnBack = findViewById(R.id.buttonCategorySelectionBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTTS.stop();
                Context context = getApplicationContext();
                goToSourceSelection(context);
            }
        });


    }

    private void submit() {

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
        mTTS.stop();
    }

    private void PrevButton () {
        while (setNum == 2) {
            setNum--;
            btnPrev.setAlpha((float) 0.5);
            btnNext.setAlpha((float) 1);
        }
    }

    private void nextButton () {
        while (setNum == 1) {
            setNum++;
            btnPrev.setAlpha((float) 1);
            btnNext.setAlpha((float) 0.5);
        }
    }


    private void absPrevButton () {
        PrevButton();
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
//                    TextReader.invalidInput(mTTS, tv, 5);
                    String temp = "There are only 5 choices. Try again. " + tv.getText().toString();
                    TextReader.sayText(mTTS, temp);
                } else {
                    category = getAbsCategoryString();
                    goToArticleSelectionActivity(context, source, category, subcategory);
                }
                break;
            case 2:
                if ( (categoryInt < 1)|| (categoryInt > 2) ) {
                    String temp = "There are only 2 choices. Try again. " + tv.getText().toString();
                    TextReader.sayText(mTTS, temp);
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


    private void gmaPrevButton () {
        switch (setNum) {
            case 2: // -> 1
                setNum--;
                btnPrev.setAlpha((float) 0.5);
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
                btnPrev.setAlpha((float) 1);
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
                    String temp = "There are only 5 choices. Try again. " + tv.getText().toString();
                    TextReader.sayText(mTTS, temp);
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
                    String temp = "There is only 1 choice. Try again. " + tv.getText().toString();
                    TextReader.sayText(mTTS, temp);
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
                        output = "lifestyLe";
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



    private void inquirerPrevButton () {
        PrevButton();
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
                    String temp = "There are only 5 choices. Try again. " + tv.getText().toString();
                    TextReader.sayText(mTTS, temp);
                } else {
                    category = getInquirerCategoryString();
                    goToSubcategorySelectionActivity(context, source, category);
                }
                break;
            case 2:
                if ( (categoryInt < 1)|| (categoryInt > 3) ) {
                    String temp = "There are only 3 choices. Try again. " + tv.getText().toString();
                    TextReader.sayText(mTTS, temp);
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
    //prbably sa Prev and next button check your code lalo sa increment at decrement


    private void philstarPrevButton () {
        PrevButton();
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
                    String temp = "There are only 5 choices. Try again. " + tv.getText().toString();
                    TextReader.sayText(mTTS, temp);
                } else {
                    category = getPhilstarCategoryString();
                    goToArticleSelectionActivity(context, source, category, subcategory);
                }
                break;
            case 2:
                if ( (categoryInt < 1)|| (categoryInt > 4) ) {
                    String temp = "There are only 4 choices. Try again. " + tv.getText().toString();
                    TextReader.sayText(mTTS, temp);
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

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Context context = getApplicationContext();
        mTTS = TextReader.initialize(context);
        TextReader.say(mTTS, tv);
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



