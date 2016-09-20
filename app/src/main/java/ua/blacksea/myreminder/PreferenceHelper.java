package ua.blacksea.myreminder;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Igor on 07.09.2016.
 */
public class PreferenceHelper {
    public static final String SPLASH_IS_INVISIBLE = "splash is invisible";
    private static PreferenceHelper instance;
    private Context context;
    private SharedPreferences preferences;

    private PreferenceHelper(){

    }

    public static PreferenceHelper getInstance(){
        if(instance == null){
            instance = new PreferenceHelper();
        }
        return instance;
    }

    public void init(Context context){
        this.context = context;
        preferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
    }

    public void putBoolean(String key, boolean value){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
    public boolean getBoolean(String key){
        return  preferences.getBoolean(key, false);
    }
}
