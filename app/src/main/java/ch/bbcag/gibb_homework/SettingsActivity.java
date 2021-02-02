package ch.bbcag.gibb_homework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import ch.bbcag.gibb_homework.components.module.list.ModuleListAdapter;
import ch.bbcag.gibb_homework.dal.ModuleDAO;
import ch.bbcag.gibb_homework.model.Module;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ModuleDAO moduleDAO = new ModuleDAO(this);
        ArrayList<Module> modules = moduleDAO.all();
        ModuleListAdapter moduleListAdapter = new ModuleListAdapter(new ArrayList<>(), this);
        moduleListAdapter.addAll(modules);
        ListView settingsModuleList = findViewById(R.id.settings_module_list);
        settingsModuleList.setAdapter(moduleListAdapter);
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
}