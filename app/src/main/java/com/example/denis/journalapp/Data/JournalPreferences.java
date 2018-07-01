package com.example.denis.journalapp.Data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by denis on 01/07/18.
 */

public class JournalPreferences {
    private static final String PREF_USER_NAME = "user_name";
    private static final String PREF_USER_EMAIL = "user_email";
    private static final String PREF_SIGNED_IN = "is_signed_in";

    public static void setUserDetails(Context context, String userName, String email,
                                      Boolean isSignedIn) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();

        editor.putString(PREF_USER_NAME, userName);
        editor.putString(PREF_USER_EMAIL, email);
        editor.putBoolean(PREF_SIGNED_IN, isSignedIn);
        editor.apply();
    }

    public static boolean checkIfSignedIn(Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        Boolean isSignedIn = sp.getBoolean(PREF_SIGNED_IN, false);

        return isSignedIn;
    }

    public static void clearPreferences(Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();

        editor.clear();
        editor.commit();
    }
}
