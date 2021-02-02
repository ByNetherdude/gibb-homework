package ch.bbcag.gibb_homework.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ch.bbcag.gibb_homework.database.ModuleEntry;
import ch.bbcag.gibb_homework.database.TaskEntry;
import ch.bbcag.gibb_homework.helper.DatabaseHelper;
import ch.bbcag.gibb_homework.model.Module;
import ch.bbcag.gibb_homework.model.Task;

public class TaskDAO {

    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    public TaskDAO(Context ctx) {
        dbHelper = new DatabaseHelper(ctx);
        db = dbHelper.getWritableDatabase();
    }

    public ArrayList<Task> all() {

        // the elements of the following String Array projectionTask represent
        // which columns should get selected from the task table
        String[] projectionTask = {
                TaskEntry.COLUMN_ID,
                TaskEntry.COLUMN_TITLE,
                TaskEntry.COLUMN_DESCRIPTION,
                TaskEntry.COLUMN_DUE_DATE,
                TaskEntry.COLUMN_DUE_DATE_TIMESTAMP,
                TaskEntry.COLUMN_MODULE_ID,
                TaskEntry.COLUMN_IS_DONE,
                TaskEntry.COLUMN_IMAGE_FILE
        };


        // in the following two lines the ordering scheme for all selected rows should get specified
        String sortOrderTask = TaskEntry.COLUMN_DUE_DATE_TIMESTAMP+" DESC";


        // the db.query() method executes the select query with arguments as to from what table which
        // columns and in what order the rows should get selected and returns a cursor object which represents
        // the result as table-like data structure

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

        // for each selected row in the cursor the code will now generate a corresponding java object
        // all objects will get stored in the ArrayList result
        ArrayList<Task> result = new ArrayList<Task>();
        while (cursorTask.moveToNext()) {
            Task task = new Task();
            task.setModule(getTaskModule(cursorTask.getInt(cursorTask.getColumnIndex(TaskEntry.COLUMN_MODULE_ID))));
            task.setId(cursorTask.getInt(cursorTask.getColumnIndex(TaskEntry.COLUMN_ID)));
            task.setTitle(cursorTask.getString(cursorTask.getColumnIndex(TaskEntry.COLUMN_TITLE)));
            task.setDescription(cursorTask.getString(cursorTask.getColumnIndex(TaskEntry.COLUMN_DESCRIPTION)));
            task.setDueDate(cursorTask.getString(cursorTask.getColumnIndex(TaskEntry.COLUMN_DUE_DATE)));
            task.setDueDateTimestamp(parseDueDate(cursorTask.getString(cursorTask.getColumnIndex(TaskEntry.COLUMN_DUE_DATE))));
            task.setModuleId(cursorTask.getInt(cursorTask.getColumnIndex(TaskEntry.COLUMN_MODULE_ID)));
            task.setDone(cursorTask.getInt(cursorTask.getColumnIndex(TaskEntry.COLUMN_IS_DONE)) > 0);
            task.setImageFile(cursorTask.getString(cursorTask.getColumnIndex(TaskEntry.COLUMN_IMAGE_FILE)));
            result.add(task);
        }

        Log.d("DATABASE", "List of tasks: "+result);
        cursorTask.close();

        // after the code has generated an object for each row of the cursor object and stored the object
        // in the ArrayList result we can close the cursor object and return the result ArrayList
        return result;
    }

    public void add(String title, String description, String dueDate, int moduleId, String imageFile, int isDone) {
        ContentValues values = new ContentValues();

        int dueDateTimestamp = parseDueDate(dueDate);

        values.put(TaskEntry.COLUMN_TITLE, title);
        values.put(TaskEntry.COLUMN_DESCRIPTION, description);
        values.put(TaskEntry.COLUMN_DUE_DATE, dueDate);
        values.put(TaskEntry.COLUMN_DUE_DATE_TIMESTAMP, dueDateTimestamp);
        values.put(TaskEntry.COLUMN_MODULE_ID, moduleId);
        values.put(TaskEntry.COLUMN_IMAGE_FILE, imageFile);
        values.put(TaskEntry.COLUMN_IS_DONE, isDone);

        db.insert(TaskEntry.TABLE_NAME, null, values);
    }

    public void delete(int id) {
        String selection = String.format("%s = ?", TaskEntry.COLUMN_ID);

        String[] selectionArgs = {String.valueOf(id)};

        db.delete(TaskEntry.TABLE_NAME, selection, selectionArgs);
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

        // TODO also update dueDateTimestamp

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

    public void updateIsDone(Task task, boolean isDone) {
        ContentValues values = new ContentValues();
        int isDoneInt = isDone ? 1 : 0;
        values.put(TaskEntry.COLUMN_TITLE, isDoneInt);

        String selection = String.format("%s = ?", TaskEntry.COLUMN_ID);
        String[] selectionArgs = {String.valueOf(task.getId())};

        db.update(
                TaskEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );
    }

    public int parseDueDate(String dueDate) {
        // magic number=
        // millisec * sec * min * hours
        // 1000 * 60 * 60 * 24 = 86400000
        long MAGIC=86400000L;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.mm.yy");
            Date parsedDate = dateFormat.parse(dueDate);
            long currentTime = parsedDate.getTime();
            currentTime = currentTime / MAGIC;
            return (int) currentTime;
        } catch(Exception e) { //this generic but you can control another types of exception
            Log.d("ERROR", "Cant parse dueDate", e);
        }

        return 0;
    }

    /**
     * Finds the module that is related to the module with the given id
     * @param id
     * @return
     */
    public Module getTaskModule(int id) {
        String[] projectionModule = {
                ModuleEntry.COLUMN_ID,
                ModuleEntry.COLUMN_NUMBER,
                ModuleEntry.COLUMN_TITLE,
                ModuleEntry.COLUMN_IS_ACTIVE,
                ModuleEntry.COLUMN_COLOR,
        };

        String sortOrderModule = String.format("%s ASC", ModuleEntry.COLUMN_NUMBER);
        sortOrderModule = ModuleEntry.COLUMN_NUMBER+" ASC";

        String selection = String.format("%s = ?", ModuleEntry.COLUMN_ID);
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursorModule = db.query(
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

        cursorModule.moveToNext();
        Module module = new Module();

        module.setId(cursorModule.getInt(cursorModule.getColumnIndex(ModuleEntry.COLUMN_ID)));
        module.setNumber(cursorModule.getString(cursorModule.getColumnIndex(ModuleEntry.COLUMN_NUMBER)));
        module.setTitle(cursorModule.getString(cursorModule.getColumnIndex(ModuleEntry.COLUMN_TITLE)));
        module.setColor(cursorModule.getString(cursorModule.getColumnIndex(ModuleEntry.COLUMN_COLOR)));
        module.setActive(cursorModule.getInt(cursorModule.getColumnIndex(ModuleEntry.COLUMN_IS_ACTIVE)) > 0);

        return module;
    }
}
