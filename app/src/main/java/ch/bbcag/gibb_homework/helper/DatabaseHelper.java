package ch.bbcag.gibb_homework.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import ch.bbcag.gibb_homework.R;

public class DatabaseHelper extends SQLiteOpenHelper {
    /*
     * The Android's default system path of the application database in internal
     * storage. The package of the application is part of the path of the
     * directory.
     */
    private static String DB_DIR = "/data/data/ch.bbcag.gibb_homework/databases/";
    private static String DB_NAME = "gibbHWDB.sqlite";
    private static String DB_PATH = DB_DIR + DB_NAME;
    private static String OLD_DB_PATH = DB_DIR + "old_" + DB_NAME;

    private final Context CTX;

    private boolean isCreated = false;
    private boolean isUpgraded = false;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, context.getResources().getInteger(R.integer.databaseVersion));
        CTX = context;
        DB_PATH = CTX.getDatabasePath(DB_NAME).getAbsolutePath();
        Log.d("DATABASE", "Database created: \n\t"+DB_PATH+"\n\t"+DB_DIR);
    }

    public void initializeDB() {
        getWritableDatabase();

        if (isCreated) {
            try {
                copyDB();
            } catch (IOException e) {
                throw new Error("Error copying database", e);
            }
        } else if (isUpgraded) {
            try {
                FileHelper.copyFile(DB_PATH, OLD_DB_PATH);
                copyDB();
            } catch (IOException e) {
                throw new Error("Error copying database", e);
            }
        }
    }

    private void copyDB() throws IOException {
        close();

        InputStream input = CTX.getAssets().open(DB_NAME);
        OutputStream output = new FileOutputStream(DB_PATH);

        FileHelper.copyFile(input, output);

        getWritableDatabase().close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        isCreated = true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        isUpgraded = true;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
}
