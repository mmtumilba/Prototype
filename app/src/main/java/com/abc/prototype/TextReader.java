package com.abc.prototype;

import android.content.Context;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Locale;

public class TextReader {

    public static TextToSpeech initialize (Context context) {
        TextToSpeech tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {   //initialize textToSpeech
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
//                    int result = mTTS.setLanguage(Locale.CANADA);
//                    TextReader.say(mTTS, tv);
                } else {
                    Log.e("TTS", "Initialization failed");
                }
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
                reader.setSpeechRate(1);
                reader.setPitch(1);
                reader.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            }
        }.start();
    }

    public static void invalidInput (TextToSpeech reader, TextView tv) {
        reader.speak("Try again.", TextToSpeech.QUEUE_FLUSH, null);
        TextReader.say(reader, tv);
    }

}
