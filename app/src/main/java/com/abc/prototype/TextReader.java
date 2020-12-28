package com.abc.prototype;

import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.widget.TextView;

public class TextReader {

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

}
