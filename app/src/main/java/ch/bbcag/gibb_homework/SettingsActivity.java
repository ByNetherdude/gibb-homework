package ch.bbcag.gibb_homework;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        ModuleDAO moduleDAO = new ModuleDAO(this);
        ArrayList<Module> modules = moduleDAO.all();
        ModuleListAdapter moduleListAdapter = new ModuleListAdapter(modules, this);
        moduleListAdapter.addAll(modules);
        ListView taskList = findViewById(R.id.task_list);
        taskList.setAdapter(moduleListAdapter);
    }
}