package ch.bbcag.gibb_homework;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;

import ch.bbcag.gibb_homework.constants.IntentContext;

public class CreateEditActivity extends AppCompatActivity {
    private String context;

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
}