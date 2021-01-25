package com.abc.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.util.Vector;

import static com.abc.prototype.Navigate.goToReadBookmarkActivity;
import static com.abc.prototype.Navigate.goToSourceSelection;

public class BookmarksActivity extends AppCompatActivity {

    private Vector<String> titles;
    private Vector<String> titleSets;

    private int setNum = 1;
    private int setNumIndex;
    private int setMax;

    private int titlesNum;
    private int lastSetSize;
    private int article = 0;
    private String articleIndex;


    private TextView tv;
    private TextToSpeech mTTS;

    private Button btnBack;
    private Button btnPrev;
    private Button btnNext;
    private Button btnClear;

    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;


    private boolean clearList = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);

        tv = findViewById(R.id.textViewBookmarks);
        new BookmarksThread().execute();

        Context context = getApplicationContext();
        mTTS = TextReader.initialize(context);


        btn1 = findViewById(R.id.button1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clearList) {
                    mTTS.speak("No more bookmarks.", TextToSpeech.QUEUE_FLUSH, null);
                } else {
                    article = 1;
                    submit();
                }


            }
        });

        btn2 = findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clearList) {
                    mTTS.speak("No more bookmarks.", TextToSpeech.QUEUE_FLUSH, null);
                } else {
                    article = 2;
                    submit();
                }
            }
        });

        btn3 = findViewById(R.id.button3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clearList) {
                    mTTS.speak("No more bookmarks.", TextToSpeech.QUEUE_FLUSH, null);
                } else {
                    article = 3;
                    submit();
                }
            }
        });


        Button btn4 = findViewById(R.id.button4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clearList) {
                    mTTS.speak("No more bookmarks.", TextToSpeech.QUEUE_FLUSH, null);
                } else {
                    article = 4;
                    submit();
                }
            }
        });

        btn5 = findViewById(R.id.button5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clearList) {
                    mTTS.speak("No more bookmarks.", TextToSpeech.QUEUE_FLUSH, null);
                } else {
                    article = 5;
                    submit();
                }
            }
        });


        btnPrev = findViewById(R.id.buttonBookmarksPrev);
        btnPrev.setAlpha((float) 0.5);
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrevButton();
            }
        });

        btnNext = findViewById(R.id.buttonBookmarksNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextButton();
            }
        });


        btnClear = findViewById(R.id.buttonClearList);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                File newXML = new File(context.getFilesDir() + "/bookmarks.xml");
                boolean deleted = newXML.delete();

                clearList = true;

                tv.setText("");
                String text = (String) getText(R.string.clear_list);
                mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        btnBack = findViewById(R.id.buttonBookmarksBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTTS.stop();
                Context context = getApplicationContext();
                goToSourceSelection(context);
            }
        });


    }


//    private void determineClickability () {
//        if (setNum == setMax) {
//            switch (lastSetSize) {
//                case 1:
//                    btn2.setClickable(false);
//                    btn3.setClickable(false);
//                    btn4.setClickable(false);
//                    btn5.setClickable(false);
//                    Log.e("clickability", "1 button");
//                    break;
//                case 2:
//                    btn3.setClickable(false);
//                    btn4.setClickable(false);
//                    btn5.setClickable(false);
//                    Log.e("clickability", "2 buttons");
//                    break;
//                case 3:
//                    btn4.setClickable(false);
//                    btn5.setClickable(false);
//                    Log.e("clickability", "3 buttons");
//                    break;
//                case 4:
//                    btn5.setClickable(false);
//                    Log.e("clickability", "4 buttons");
//                    break;
//                case 5:
//                    Log.e("clickability", "5 buttons");
//                    break;
//
//            }
//        }
//    }

    private void submit() {
        Context context = getApplicationContext();

        int val = article + (setNumIndex * 5) - 1;
        articleIndex = Integer.toString(val);


        if (lastSetSize == 0) {
            if ( (article < 1) || (article > 5) ) {
                String temp = "There are only 5 choices. Try again. " + tv.getText().toString();
                TextReader.sayText(mTTS, temp);
            } else {
                goToReadBookmarkActivity(context, articleIndex);
            }
        } else {
            if (setNum == setMax) {
                if ( (article < 1) || (article > lastSetSize) ) {
                    if (lastSetSize == 1) {
                        String temp = "There is only " + lastSetSize + " choice. Try again. " + tv.getText().toString();
                        TextReader.sayText(mTTS, temp);
                    } else {
                        String temp = "There are only " + lastSetSize + " choices. Try again. " + tv.getText().toString();
                        TextReader.sayText(mTTS, temp);
                    }

                } else {
                    goToReadBookmarkActivity(context, articleIndex);
                }
            } else {
                if ( (article < 1) || (article > 5) ) {
                    String temp = "There are only 5 choices. Try again. " + tv.getText().toString();
                    TextReader.sayText(mTTS, temp);
                } else {
                    goToReadBookmarkActivity(context, articleIndex);
                }
            }
        }
        mTTS.stop();
        Log.e("articleIndexVal", articleIndex);
    }

    private void PrevButton() {
        mTTS.stop();

        if (setMax == 1) {
            btnPrev.setAlpha((float) 0.5);
            btnNext.setAlpha((float) 0.5);
        } else {
            if (setNum == 2) {
                btnPrev.setAlpha((float) 0.5);
            }
            if (setNum == setMax) {
                btnNext.setAlpha((float) 1);
            }
            if (setNum > 1) {
                setNum--;
                setNumIndex = setNum - 1;
                tv.setText(titleSets.get(setNumIndex));
                mTTS.stop();
                TextReader.say(mTTS, tv);
            }
        }
    }

    private void nextButton () {
        mTTS.stop();

        if (setMax == 1) {
            btnPrev.setAlpha((float) 0.5);
            btnNext.setAlpha((float) 0.5);
        } else {
            int temp = setMax - 1;
            if (setNum == (temp)) {
                btnNext.setAlpha((float) 0.5);
            }
            if (setNum == 1) {
                btnPrev.setAlpha((float) 1.0);
            }
            if (setNum < setMax) {
                setNum++;
                setNumIndex = setNum - 1;
                tv.setText(titleSets.get(setNumIndex));
                mTTS.stop();
                TextReader.say(mTTS, tv);
            }
        }
    }




    private class BookmarksThread extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            Context context = getApplicationContext();

//            if wala pa file say no items in list
            File newXML = new File(context.getFilesDir() + "/bookmarks.xml");
            if (newXML.exists()) {
                Log.e("EXISTENCE", "IT LIIIIVESS");

                Bookmark bookmark = new Bookmark(context);
                titles = bookmark.titles;
                titleSets = bookmark.titleSets;

                titlesNum = titles.size();
                lastSetSize = titlesNum % 5;
                setMax = titleSets.size();

            } else {
                Log.e("EXISTENCE", "MUST CREATE");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);



            Context context = getApplicationContext();
            File newXML = new File(context.getFilesDir() + "/bookmarks.xml");
            if (newXML.exists()) {
                Log.e("EXISTENCE", "IT LIIIIVESS");
                if (setMax == 1 ){
                btnNext.setAlpha((float) 0.5);
            }


            if (titlesNum == 1) {
                String temp = "There is " + titlesNum + " bookmark. \n" + titleSets.get(0);
                tv.setText(temp);
            } else {
                String temp = "There are " + titlesNum + " bookmarks. \n" + titleSets.get(0);
                tv.setText(temp);

            }

            } else {
                Log.e("EXISTENCE", "MUST CREATE");
                tv.setText(R.string.empty);
            }
        }


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