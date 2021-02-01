package ch.bbcag.gibb_homework.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ch.bbcag.gibb_homework.database.ModuleEntry;
import ch.bbcag.gibb_homework.database.TaskEntry;
import ch.bbcag.gibb_homework.helper.DatabaseHelper;
import ch.bbcag.gibb_homework.model.Modul;
import ch.bbcag.gibb_homework.model.Task;

public class TaskDAO {

    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    public TaskDAO(Context ctx) {
        dbHelper = new DatabaseHelper(ctx);
        db = dbHelper.getWritableDatabase();
    }

    public ArrayList<Task> all() {
        String[] projectionTask = {
                TaskEntry.COLUMN_ID,
                TaskEntry.COLUMN_TITLE,
                TaskEntry.COLUMN_DESCRIPTION,
                TaskEntry.COLUMN_DUE_DATE,
                TaskEntry.COLUMN_MODULE_ID
        };

        String sortOrderTask = String.format("%s ASC", TaskEntry.COLUMN_DUE_DATE);
        sortOrderTask = TaskEntry.COLUMN_DUE_DATE+" ASC";

        Cursor cursorTask = db.query(
                false,
                TaskEntry.TABLE_NAME, // tableName
                projectionTask, // columns
                null,
                null,
                null,
                null,
                sortOrderTask, // orderBy
                null,
                null
        );

        ArrayList<Task> result = new ArrayList<Task>();
        while (cursorTask.moveToNext()) {
            Task task = new Task();
            task.setModul(getTaskModule(cursorTask.getInt(cursorTask.getColumnIndex(TaskEntry.COLUMN_MODULE_ID))));
            task.setId(cursorTask.getInt(cursorTask.getColumnIndex(TaskEntry.COLUMN_ID)));
            task.setTitle(cursorTask.getString(cursorTask.getColumnIndex(TaskEntry.COLUMN_TITLE)));
            task.setDescription(cursorTask.getString(cursorTask.getColumnIndex(TaskEntry.COLUMN_DESCRIPTION)));
            task.setDueDate(cursorTask.getString(cursorTask.getColumnIndex(TaskEntry.COLUMN_DUE_DATE)));
            task.setModulId(cursorTask.getInt(cursorTask.getColumnIndex(TaskEntry.COLUMN_MODULE_ID)));

            result.add(task);
        }

        Log.d("DATABASE", "Hallo: "+result);
        cursorTask.close();

        return result;
    }

    public void add(String title, String description, String dueDate, int moduleId) {
        ContentValues values = new ContentValues();

        values.put(TaskEntry.COLUMN_TITLE, title);
        values.put(TaskEntry.COLUMN_DESCRIPTION, description);
        values.put(TaskEntry.COLUMN_DUE_DATE, dueDate);
        values.put(TaskEntry.COLUMN_MODULE_ID, moduleId);

        db.insert(TaskEntry.TABLE_NAME, null, values);
    }

    public void delete(int id) {
        String selection = "id = " + id;

        db.delete(TaskEntry.TABLE_NAME, selection, null);
    }

    public void updateTitle(Task task, String title) {
        ContentValues values = new ContentValues();
        values.put(TaskEntry.COLUMN_TITLE, title);

        String selection = String.format("%s = ?", TaskEntry.COLUMN_ID);
        String[] selectionArgs = {String.valueOf(task.getId())};

        db.update(
                TaskEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );
    }


    public void updateDescription(Task task, String description) {
        ContentValues values = new ContentValues();
        values.put(TaskEntry.COLUMN_TITLE, description);

        String selection = String.format("%s = ?", TaskEntry.COLUMN_ID);
        String[] selectionArgs = {String.valueOf(task.getId())};

        db.update(
                TaskEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );
    }

    public void updateDueDate(Task task, String dueDate) {
        ContentValues values = new ContentValues();
        values.put(TaskEntry.COLUMN_TITLE, dueDate);

        String selection = String.format("%s = ?", TaskEntry.COLUMN_ID);
        String[] selectionArgs = {String.valueOf(task.getId())};

        db.update(
                TaskEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );
    }

    public void updateImageFile(Task task, String imageFile) {
        ContentValues values = new ContentValues();
        values.put(TaskEntry.COLUMN_TITLE, imageFile);

        String selection = String.format("%s = ?", TaskEntry.COLUMN_ID);
        String[] selectionArgs = {String.valueOf(task.getId())};

        db.update(
                TaskEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );
    }

    public Modul getTaskModule(int id) {
        String[] projectionModul = {
                ModuleEntry.COLUMN_ID,
                ModuleEntry.COLUMN_NUMBER,
                ModuleEntry.COLUMN_TITLE,
                ModuleEntry.COLUMN_IS_ACTIVE,
                ModuleEntry.COLUMN_COLOR,
        };

        String sortOrderModul = String.format("%s ASC", ModuleEntry.COLUMN_NUMBER);
        sortOrderModul = ModuleEntry.COLUMN_NUMBER+" ASC";

        Cursor cursorModul = db.query(
                false,
                ModuleEntry.TABLE_NAME, // tableName
                projectionModul, // columns
                null,
                null,
                null,
                null,
                sortOrderModul, // orderBy
                null,
                null
        );

        Modul modul = null;
        boolean nextModul = true;
        while (cursorModul.moveToNext() && nextModul) {
            int modulId = cursorModul.getInt(cursorModul.getColumnIndex(ModuleEntry.COLUMN_ID));

            if (modulId == id) {
                modul = new Modul();
                modul.setId(cursorModul.getInt(cursorModul.getColumnIndex(ModuleEntry.COLUMN_ID)));
                modul.setNumber(cursorModul.getString(cursorModul.getColumnIndex(ModuleEntry.COLUMN_NUMBER)));
                modul.setTitle(cursorModul.getString(cursorModul.getColumnIndex(ModuleEntry.COLUMN_TITLE)));
                modul.setColor(cursorModul.getString(cursorModul.getColumnIndex(ModuleEntry.COLUMN_COLOR)));

                nextModul = false;
                return modul;
            }
        }

        return modul;
    }
}
