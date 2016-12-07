package ua.blacksea.myreminder;

import android.app.Application;

/**
 * Created by Игорь on 12/7/2016.
 */

public class MyApp extends Application {

    private static boolean activityVisible;

    public static boolean isActivityVisible(){
        return activityVisible;
    }

    public static void activityResumed(){
        activityVisible = true;
    }

    public static void activityPaused(){
        activityVisible = false;
    }
}
