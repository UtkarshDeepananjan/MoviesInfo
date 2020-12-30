package com.uds.popularmovies.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceUtils {
    private static final String PREF_SELECTED_MENU = "selected_menu";
    private static final String DEFAULT_SELECTED_MENU = "POPULAR";

    public static void setSelectedMenu(Context context, String movieMenu) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(PREF_SELECTED_MENU, movieMenu);
        editor.apply();
    }

    public static String getSelectedMenu(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_SELECTED_MENU, DEFAULT_SELECTED_MENU);
    }
}
