package ch.bbcag.gibb_homework.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ch.bbcag.gibb_homework.R;

public class DatabaseHelper extends SQLiteOpenHelper {
    /*
     * The Android's default system path of the application database in internal
     * storage. The package of the application is part of the path of the
     * directory.
     */
    private static String DB_DIR = "/data/data/gibbhomework/databases/";
    private static String DB_NAME = "gibbHWDB.sqlite";
    private static String DB_PATH = DB_DIR + DB_NAME;

    private final Context CXT;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, context.getResources().getInteger(R.integer.databaseVersion));
        CXT = context;
        DB_PATH = CXT.getDatabasePath(DB_NAME).getAbsolutePath();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
}
