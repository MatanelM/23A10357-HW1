package com.example.lanesproject.managers;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class GsonManager {

    private static final String DB_FILE = "DB_FILE";

    private static GsonManager instance = null;
    private SharedPreferences preferences;

    private GsonManager(Context context){
        preferences = context.getSharedPreferences(DB_FILE,Context.MODE_PRIVATE);
    }

    public static void init(Context context){
        if (instance == null)
            instance = new GsonManager(context);
    }

    public static GsonManager getInstance() {

        return instance;
    }

    public void putInt(String key, int value) {

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getInt(String key, int defValue) {

        return preferences.getInt(key, defValue);
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key, String defValue) {
        return preferences.getString(key, defValue);
    }

    public String toGson(Object obj){
        return new Gson().toJson(obj);
    }

    public <T> T fromJson(String key, Class<T> cls){
        return new Gson().fromJson(key, cls);
    }
}
