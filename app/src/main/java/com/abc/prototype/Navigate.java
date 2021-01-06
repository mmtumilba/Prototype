package com.abc.prototype;

import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class Navigate {

    public static void goToSourceSelection (Context context) {
        Intent intent = new Intent(context, SourceSelectionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    public static void goToCategorySelectionActivity (Context context, String sourceStr) {
        Log.e("CHECKPOINT", "nakapasok CategorySelectionActivity");

        Intent intent = new Intent(context, CategorySelectionActivity.class);
        intent.putExtra("source", sourceStr);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

        Log.e("CHECKPOINT", "palabas ng navigate to CategorySelectionActivity");

    }

    /*
     * public static void goToArticleSelectionActivity (Context context, String categoryStr, String subcategoryStr) {
     *         Intent intent = new Intent(context, CategorySelectionActivity.class);
     *         intent.putExtra("category", categoryStr);
     *         intent.putExtra("subcategory", subcategoryStr);
     *         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
     *         context.startActivity(intent);
     *
     * }
     */
}
