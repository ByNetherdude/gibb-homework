package ch.bbcag.gibb_homework;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ch.bbcag.gibb_homework.components.tasks.list.TaskAdapter;
import ch.bbcag.gibb_homework.constants.IntentContext;
import ch.bbcag.gibb_homework.dal.TaskDAO;
import ch.bbcag.gibb_homework.helper.DatabaseHelper;
import ch.bbcag.gibb_homework.model.Task;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DatabaseHelper dbHelper;
        SQLiteDatabase gibbHWDB = null;

        dbHelper = new DatabaseHelper(this);
        Log.d("DATABASE", "Initializing Database");
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

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callCreate = new Intent(MainActivity.this, CreateEditActivity.class);
                callCreate.putExtra(IntentContext.NAME, IntentContext.CONTEXT_CREATE);
                startActivity(callCreate);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        TaskDAO taskDAO = new TaskDAO(this);
        ArrayList<Task> ttask = taskDAO.all();
        TaskAdapter taskAdapter = new TaskAdapter(ttask, this);
        taskAdapter.addAll(ttask);
        ListView taskList = findViewById(R.id.task_list);
        taskList.setAdapter(taskAdapter);
        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Task task = (Task) adapterView.getItemAtPosition(i);
                Intent callDetail = new Intent(MainActivity.this, DetailActivity.class);
                callDetail.putExtra("Task", task);
                startActivity(callDetail);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}