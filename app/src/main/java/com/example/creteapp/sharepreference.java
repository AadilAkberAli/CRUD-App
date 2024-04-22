package com.example.creteapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class sharepreference {

    static final String PREF_USER_NAME= "username";
    static final String PREF_EMAIL_ADDRESS= "emailaddress";
    static final String PREF_USER_ID = "userid";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setuserdetails(Context ctx, String userName , String email, Integer user_id)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.putString(PREF_EMAIL_ADDRESS, email);
        editor.putInt(PREF_USER_ID, user_id);
        editor.commit();
    }

    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, null);
    }
    public static String getPrefEmailAddress(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_EMAIL_ADDRESS, null);
    }
    public static Integer getPrefUserId(Context ctx)
    {
        return getSharedPreferences(ctx).getInt(PREF_USER_ID, 0);
    }

    public static void cleardata(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear(); //clear all stored data
        editor.commit();
    }
}
