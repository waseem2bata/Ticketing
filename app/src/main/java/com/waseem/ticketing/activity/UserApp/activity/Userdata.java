package com.waseem.ticketing.activity.UserApp.activity;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by waseem on 6/16/2017.
 */

public class Userdata {
    private static final String SHARED_PREF_NAME = "pref";
    private static final String TAG_NAME = "tagname";
    private static final String TAG_EMAIL = "tagemail";
    private static final String TAG_TOKEN= "tagtoken";
    private static final String TAG_PASS= "tagtoken";


    private static final String TAG_UID = "";



    private static Userdata Instance;
    private static Context mcontext;

    private Userdata(Context context) {
        mcontext = context;
    }

    public static synchronized Userdata getInstance(Context context) {
        if (Instance == null) {
            Instance = new Userdata(context);
        }
        return Instance;
    }

        public boolean savedata(String token,String email,String uid,String name){
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TAG_TOKEN, token);
        editor.putString(TAG_NAME, name);
        editor.putString(TAG_EMAIL, email);
        editor.putString(TAG_UID, uid);

        editor.apply();
        return true;
    }

    public String getTAG_UID(){
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(TAG_UID, null);
    }
    public String getTAG_NAME(){
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(TAG_NAME, null);
    }
    public String getTag_Email(){
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(TAG_EMAIL, null);
    }
    public String getTag_Pass(){
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(TAG_PASS, null);
    }
    public String getTag_Tokeen(){
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(TAG_TOKEN, null);
    }

}