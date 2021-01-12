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

    public static void goToBookmarksActivity (Context context) {
        Intent intent = new Intent(context, BookmarksActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void goToChooseActionActivity (Context context, String source, String link, String title) {
        Intent intent = new Intent(context, ChooseActionActivity.class);
        intent.putExtra("source", source);
        intent.putExtra("link", link);
        intent.putExtra("title", title);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void goToCategorySelectionActivity (Context context, String sourceStr) {
        Intent intent = new Intent(context, CategorySelectionActivity.class);
        intent.putExtra("source", sourceStr);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void goToSubcategorySelectionActivity (Context context, String source, String category) {
        Intent intent = new Intent(context, SubcategorySelectionActivity.class);
        intent.putExtra("source", source);
        intent.putExtra("category", category);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


     public static void goToArticleSelectionActivity (Context context, String source, String category, String subcategory) {
         Intent intent = new Intent(context, ArticleSelectionActivity.class);
         intent.putExtra("source", source);
         intent.putExtra("category", category);
         intent.putExtra("subcategory", subcategory);
         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         context.startActivity(intent);
     }

     public static void goToSpeedActivity (Context context) {
         Intent intent = new Intent(context, SpeedActivity.class);
         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         context.startActivity(intent);
     }

    public static void goToReadArticleActivity (Context context, String source, String link, String title) {
        Intent intent = new Intent(context, ReadArticleActivity.class);
        intent.putExtra("source", source);
        intent.putExtra("link", link);
        intent.putExtra("title", title);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
