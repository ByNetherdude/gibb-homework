package ch.bbcag.gibb_homework;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import ch.bbcag.gibb_homework.model.Task;

public class DetailActivity extends AppCompatActivity {

    Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get shared Task from MainActivity and deserialize it
        task = (Task) getIntent().getSerializableExtra("Task");

        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(task.getDueDate() + " | Module: " + task.getRelatedModuleNumber());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Back-Button

        TextView taskTitle = (TextView) findViewById(R.id.taskTitle);
        taskTitle.setText(task.getTitle());

        TextView taskDescription = (TextView)findViewById(R.id.taskDescription);
        taskDescription.setText(task.getDescription());

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    // Method to configure Back/Up-Button
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                onBackPressed();
                return true;
            case R.id.action_task_edit:
                Intent callEdit = new Intent(DetailActivity.this, CreateEditActivity.class);
                callEdit.putExtra("Task", task);
                startActivity(callEdit);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task_edit, menu);
        return true;
    }

}