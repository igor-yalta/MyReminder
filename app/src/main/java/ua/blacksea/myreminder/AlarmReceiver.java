package ua.blacksea.myreminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Игорь on 12/7/2016.
 */

public class AlarmReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("title");
        long timeStamp = intent.getLongExtra("timeStamp", 0);
        long color = intent.getIntExtra("color", 0);

        Intent resultIntent = new Intent(context, MainActivity.class);

    }
}
