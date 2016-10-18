package ua.blacksea.myreminder.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ua.blacksea.myreminder.model.ModelTask;

/**
 * Created by Igor on 20.09.2016.
 */

public class DBQueryManager {
    private SQLiteDatabase db;

    DBQueryManager(SQLiteDatabase db) {
        this.db = db;
    }

    public ModelTask getTask(long timeStamp) {
        ModelTask modelTask = null;
        Cursor c = db.query(DBHelper.TASKS_TABLE, null, DBHelper.SELECTION_TIME_STAMP,
                new String[]{Long.toString(timeStamp)}, null, null, null);

        if (c.moveToFirst()) {
            String title = c.getString(c.getColumnIndex(DBHelper.TASK_TITLE_COLUMN));
            long date = c.getLong(c.getColumnIndex(DBHelper.TASK_DATE_COLUMN));
            int priority = c.getInt(c.getColumnIndex(DBHelper.TASK_PRIORITY_COLUMN));
            int status = c.getInt(c.getColumnIndex(DBHelper.TASK_STATUS_COLUMN));

            modelTask = new ModelTask(title, date, priority, status, timeStamp);
        }
        c.close();

        return modelTask;

    }

    public List<ModelTask> getTasks(String selection, String[] selectionItem, String orderBy){
        List<ModelTask> tasks = new ArrayList<>();
        Cursor c = db.query(DBHelper.TASKS_TABLE, null, selection, selectionItem, null, null, orderBy);
        if(c.moveToFirst()){
            do {
                String title = c.getString(c.getColumnIndex(DBHelper.TASK_TITLE_COLUMN));
                long date = c.getLong(c.getColumnIndex(DBHelper.TASK_DATE_COLUMN));
                int priority = c.getInt(c.getColumnIndex(DBHelper.TASK_PRIORITY_COLUMN));
                int status = c.getInt(c.getColumnIndex(DBHelper.TASK_STATUS_COLUMN));
                long timeStamp = c.getLong(c.getColumnIndex(DBHelper.TASK_TIME_STAMP_COLUMN));

                ModelTask modelTask = new ModelTask(title, date, priority, status,timeStamp);
                tasks.add(modelTask);
            }while (c.moveToNext());
        }
        c.close();

        return tasks;
    }
}
