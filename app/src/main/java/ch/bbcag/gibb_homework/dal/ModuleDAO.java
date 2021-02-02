package ch.bbcag.gibb_homework.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import ch.bbcag.gibb_homework.database.ModuleEntry;
import ch.bbcag.gibb_homework.helper.DatabaseHelper;
import ch.bbcag.gibb_homework.model.Module;

public class ModuleDAO {

    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    public ModuleDAO(Context ctx) {
        dbHelper = new DatabaseHelper(ctx);
        db = dbHelper.getWritableDatabase();
    }

    public ArrayList<Module> all() {

        // the elements of the following String Array projectionModule represent
        // which columns should get selected from the module table

        String[] projectionModule = {
                ModuleEntry.COLUMN_ID,
                ModuleEntry.COLUMN_TITLE,
                ModuleEntry.COLUMN_NUMBER,
                ModuleEntry.COLUMN_COLOR,
                ModuleEntry.COLUMN_IS_ACTIVE
        };


        String sortOrderModule = ModuleEntry.COLUMN_IS_ACTIVE+" DESC";

        // the db.query() method executes the select query with arguments as to from what table which
        // columns and in what order the rows should get selected and returns a cursor object which represents
        // the result as table-like data structure

        Cursor cursorModule = db.query(
                false,
                ModuleEntry.TABLE_NAME, // tableName
                projectionModule, // columns
                null,
                null,
                null,
                null,
                sortOrderModule, // orderBy
                null,
                null
        );

        // for each selected row in the cursor the code will now generate a corresponding java object
        // all objects will get stored in the ArrayList result

        ArrayList<Module> result = new ArrayList<Module>();
        while (cursorModule.moveToNext()) {
            Module module = new Module();
            module.setId(cursorModule.getInt(cursorModule.getColumnIndex(ModuleEntry.COLUMN_ID)));
            module.setTitle(cursorModule.getString(cursorModule.getColumnIndex(ModuleEntry.COLUMN_TITLE)));
            module.setNumber(cursorModule.getString(cursorModule.getColumnIndex(ModuleEntry.COLUMN_NUMBER)));
            module.setColor(cursorModule.getString(cursorModule.getColumnIndex(ModuleEntry.COLUMN_COLOR)));
            module.setActive(cursorModule.getInt(cursorModule.getColumnIndex(ModuleEntry.COLUMN_IS_ACTIVE)) > 0);

            result.add(module);
        }

        Log.d("DATABASE", "Hallo: "+result);
        cursorModule.close();

        // after the code has generated an object for each row of the cursor object and stored the object
        // in the ArrayList result we can close the cursor object and return the result ArrayList

        return result;

    }

    public ArrayList<Module> allActiveModules() {

        // method does the same as all() but filters modules by the criteria if they are active or not
        String[] projectionModule = {
                ModuleEntry.COLUMN_ID,
                ModuleEntry.COLUMN_TITLE,
                ModuleEntry.COLUMN_NUMBER,
                ModuleEntry.COLUMN_COLOR,
                ModuleEntry.COLUMN_IS_ACTIVE
        };

        String sortOrderModule = ModuleEntry.COLUMN_ID+" ASC";

        String selection = String.format("%s = ?", ModuleEntry.COLUMN_IS_ACTIVE);
        String[] selectionArgs = {"1"};

        Cursor cursorModul = db.query(
                false,
                ModuleEntry.TABLE_NAME, // tableName
                projectionModule, // columns
                selection,
                selectionArgs,
                null,
                null,
                sortOrderModule, // orderBy
                null,
                null
        );

        ArrayList<Module> result = new ArrayList<Module>();
        while (cursorModul.moveToNext()) {
            Module module = new Module();
            module.setId(cursorModul.getInt(cursorModul.getColumnIndex(ModuleEntry.COLUMN_ID)));
            module.setTitle(cursorModul.getString(cursorModul.getColumnIndex(ModuleEntry.COLUMN_TITLE)));
            module.setNumber(cursorModul.getString(cursorModul.getColumnIndex(ModuleEntry.COLUMN_NUMBER)));
            module.setColor(cursorModul.getString(cursorModul.getColumnIndex(ModuleEntry.COLUMN_COLOR)));
            module.setActive(cursorModul.getInt(cursorModul.getColumnIndex(ModuleEntry.COLUMN_IS_ACTIVE)) > 0);

            result.add(module);
        }

        Log.d("DATABASE", "Hallo: "+result);
        cursorModul.close();

        return result;

    }

    public void updateIsActive(Module module, boolean activeState){
        ContentValues values = new ContentValues();
        values.put(ModuleEntry.COLUMN_IS_ACTIVE, activeState);

        String selection = String.format("%s = ?", ModuleEntry.COLUMN_ID);
        String[] selectionArgs = {String.valueOf(module.getId())};

        db.update(
                ModuleEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );
    }



}
