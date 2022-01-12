package com.snystudio.simpleaplication.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.snystudio.simpleaplication.R;

public class SessionManager {

    Context mContext;

    public SessionManager(Context mContext) {
        this.mContext = mContext;
    }
    //SharedPreferences sharedPreferences=mContext.getSharedPreferences(mContext.getString(R.string.app_name),Context.MODE_PRIVATE);
    private static SharedPreferences getSharedPreference(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
    public void saveToken(Context context,String key,String value){

        SharedPreferences.Editor edit= getSharedPreference(context).edit();
        edit.putString(key,value);
        edit.apply();

    }

    public String fetchToken(Context context,String key){
        return getSharedPreference(context).getString(key,null);
    }


}
