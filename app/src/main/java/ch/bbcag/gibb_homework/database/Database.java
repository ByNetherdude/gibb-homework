package ch.bbcag.gibb_homework.database;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;

import ch.bbcag.gibb_homework.R;
import ch.bbcag.gibb_homework.helper.DatabaseHelper;

public class Database extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHelper dbHelper;
        SQLiteDatabase gibbHWDB = null;

        dbHelper = new DatabaseHelper(this);
        dbHelper.initializeDB();

        try {
            gibbHWDB = dbHelper.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                dbHelper.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                gibbHWDB.close();
            }
        }
    }
}
