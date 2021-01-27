package ch.bbcag.gibb_homework.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ch.bbcag.gibb_homework.database.TaskEntry;
import ch.bbcag.gibb_homework.helper.DatabaseHelper;
import ch.bbcag.gibb_homework.model.Task;

public class TaskDAO {

    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    private TaskDAO(Context ctx) {
        dbHelper = new DatabaseHelper(ctx);
        db = dbHelper.getWritableDatabase();
    }

    public List<Task> all() {
        String[] projection = {
                TaskEntry.COLUMN_ID,
                TaskEntry.COLUMN_TITLE,
                TaskEntry.COLUMN_DESCRIPTION,
                TaskEntry.COLUMN_DUE_DATE,
                TaskEntry.COLUMN_MODULE_ID
        };

        String sortOrder = String.format("%s ASC", TaskEntry.COLUMN_DUE_DATE);
        sortOrder = TaskEntry.COLUMN_DUE_DATE+" ASC";

        Cursor cursor = db.query(
                false,
                TaskEntry.TABLE_NAME, // tableName
                projection, // columns
                null,
                null,
                null,
                null,
                sortOrder, // orderBy
                null,
                null
        );

        List<Task> result = new ArrayList<>();
        while (cursor.moveToNext()) {
            Task task = new Task();
            task.setId(cursor.getInt(cursor.getColumnIndex(TaskEntry.COLUMN_ID)));
            task.setTitle(cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_TITLE)));
            task.setDescription(cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_DESCRIPTION)));
            task.setDueDate(cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_DUE_DATE)));
            task.setModuleId(cursor.getInt(cursor.getColumnIndex(TaskEntry.COLUMN_MODULE_ID)));

            result.add(task);
        }

        cursor.close();

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

    // TODO updateTitle
    //    public void updateTitle(int id, String title) {
    //
    //    }
    // TODO updateDescription
    //    public void updateDescription(int id, String description) {
    //
    //    }
    // TODO etc
}
