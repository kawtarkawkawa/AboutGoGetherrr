package com.example.android.aboutgogether;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceeManager {

    private Context context;
    private SharedPreferences sharedPreferences;

    public PreferenceeManager(Context context)
    {
        this.context = context;
        getSharedPreference();
    }

    private void getSharedPreference()
    {
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.my_preference),Context.MODE_PRIVATE);
    }

    public void writePreferencee()
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.my_preference_key),"INIT_OK");
        editor.commit();
    }
    public boolean checkPreferencee()
    {
        boolean status = false;

        if (sharedPreferences.getString(context.getString(R.string.my_preference_key),"null").equals("null"))
        {
            status = false;
        }
        else
            {
                status = true;
            }
            return status;
    }

    public void clearPreferencee()
    {
        sharedPreferences.edit().clear().commit();
    }
}
