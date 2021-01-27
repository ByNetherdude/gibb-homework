package ch.bbcag.gibb_homework;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import ch.bbcag.gibb_homework.constants.IntentContext;

public class CreateEditActivity extends AppCompatActivity {

    private String context;
    private static final int PICK_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Back-Button

        if(getIntent().getStringExtra(IntentContext.NAME) != null) {
            context = getIntent().getStringExtra(IntentContext.NAME);
            setActitvityTitle();
        } else {
            Log.e("Intent", "Invalid call!");
        }
    }

    @Override
    // Method to configure Back/Up-Button
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void setActitvityTitle() {
        if (context.equals(IntentContext.CONTEXT_CREATE)) {
            setTitle(getString(R.string.title_create)); // Access dynamic string from strings.xml
        }
        if (context.equals(IntentContext.CONTEXT_EDIT)) {
            setTitle(getString(R.string.title_edit)); // Access dynamic string from strings.xml
        }
    }

    // Triggered when user clicks on the upload button
    public void uploadImage(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }
}