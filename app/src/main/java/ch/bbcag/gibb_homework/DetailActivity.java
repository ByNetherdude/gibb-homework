package ch.bbcag.gibb_homework;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.File;

import ch.bbcag.gibb_homework.constants.IntentContext;
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
        setSupportActionBar(toolbar);
        toolbar.setTitle(task.getDueDate() + " | Module: " + task.getRelatedModuleNumber());

        TextView taskTitle = (TextView) findViewById(R.id.taskTitle);
        taskTitle.setText(task.getTitle());

        TextView taskDescription = (TextView)findViewById(R.id.taskDescription);
        taskDescription.setText(task.getDescription());

        TextView taskModule = (TextView) findViewById(R.id.taskModule);
        taskModule.setText(task.getRelatedModuleNumber());

        TextView taskDueDate = (TextView) findViewById(R.id.taskDueDate);
        taskDueDate.setText(task.getDueDate());

        ImageView imageView = (ImageView) findViewById(R.id.showImage);
        String imgFile = task.getImageFile();
        if(task.getImageFile() != null) {
            Log.d("IF", "IF");
            File image = new File("data/data/ch.bbcag.gibb_homework/images/" + task.getImageFile());
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(),bmOptions);
            //bitmap = Bitmap.createScaledBitmap(bitmap,parent.getWidth(),parent.getHeight(),true);
            imageView.setImageBitmap(bitmap);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Back-Button
    }

    @Override
    // Method to configure Back/Up-Button
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_task_edit:
                Intent callEdit = new Intent(DetailActivity.this, CreateEditActivity.class);
                callEdit.putExtra(IntentContext.NAME, IntentContext.CONTEXT_EDIT);
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