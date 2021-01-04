package com.abc.prototype;

import android.content.Context;
import android.content.Intent;

import com.abc.prototype.ABS.AbsCategorySelectionActivity;

public class Navigate {

    public static void goToSourceSelection (Context context) {
        Intent intent = new Intent(context, SourceSelectionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void goToAbsCategorySelection (Context context) {
        Intent intent = new Intent(context, AbsCategorySelectionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
