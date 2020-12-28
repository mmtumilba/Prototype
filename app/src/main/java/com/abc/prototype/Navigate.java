package com.abc.prototype;

import android.content.Context;
import android.content.Intent;

public class Navigate {

    public static void goToSourceSelection (Context context) {
        Intent intent = new Intent(context, SourceSelectionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
