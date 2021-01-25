package com.abc.prototype;

import android.content.Context;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Locale;

public class TextReader {
    public static float speed = 1;
    // slow 1/2
    // normal 1
    // fast 3/2

    public static TextToSpeech initialize (Context context) {
        TextToSpeech tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {   //initialize textToSpeech
            @Override
            public void onInit(int status) {
//                if (status == TextToSpeech.SUCCESS) {
////                    int result = mTTS.setLanguage(Locale.CANADA);
////                    TextReader.say(mTTS, tv);
//                } else {
//                    Log.e("TTS", "Initialization failed");
//                }
            }
        });
        return tts;
    }

    public static void say(final TextToSpeech reader, final TextView tv) {
        new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                String text = tv.getText().toString();
                reader.setSpeechRate(speed);
                reader.setPitch(1);
                reader.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            }
        }.start();
    }

    public static void sayText(final TextToSpeech reader, final String text) {
        new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                reader.setSpeechRate(speed);
                reader.setPitch(1);
                reader.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            }
        }.start();
    }

    //there are only n choices
//    public static void invalidInput (TextToSpeech reader, TextView tv, int choices) {
//        String temp = "There are only " + choices + ". Trey again. " + tv.getText();
////        reader.speak("Invalid input. Try again.", TextToSpeech.QUEUE_FLUSH, null);
//        reader.speak(temp,TextToSpeech.QUEUE_FLUSH, null);
//    }

}
