package ua.blacksea.myreminder.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import ua.blacksea.myreminder.model.ModelTask;

/**
 * Created by Igor on 20.09.2016.
 */

public class DBUpdateManager {

    SQLiteDatabase db;
    DBUpdateManager(SQLiteDatabase db){
        this.db = db;
    }

    private void title(long timeStamp, String title){
        update(DBHelper.TASK_TIME_STAMP_COLUMN, timeStamp, title);
    }
    private void date(long timeStamp, long date){
        update(DBHelper.TASK_TIME_STAMP_COLUMN, timeStamp, date);
    }
    private void priority(long timeStamp, int priority ){
        update(DBHelper.TASK_TIME_STAMP_COLUMN, timeStamp, priority);
    }
    public void status(long timeStamp, int status){
        update(DBHelper.TASK_TIME_STAMP_COLUMN, timeStamp, status);
    }
    private void task(ModelTask task){
        title(task.getTimeStamp(), task.getTitle());
        date(task.getTimeStamp(), task.getDate());
        priority(task.getTimeStamp(), task.getPriority());
        status(task.getTimeStamp(), task.getStatus());
    }

    private void update(String column, long key, String value){
        ContentValues contentValues = new ContentValues();
        contentValues.put(column, value);
        db.update(DBHelper.TASKS_TABLE, contentValues, DBHelper.TASK_TIME_STAMP_COLUMN + " - " + key, null);
    }

    private void update(String column, long key, long value){
        ContentValues contentValues = new ContentValues();
        contentValues.put(column, value);
        db.update(DBHelper.TASKS_TABLE, contentValues, DBHelper.TASK_TIME_STAMP_COLUMN + " - " + key, null);
    }
}
