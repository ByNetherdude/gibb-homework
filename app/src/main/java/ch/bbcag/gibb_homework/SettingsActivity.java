package ch.bbcag.gibb_homework;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import ch.bbcag.gibb_homework.components.modul.list.ModulListAdapter;
import ch.bbcag.gibb_homework.dal.ModuleDAO;
import ch.bbcag.gibb_homework.model.Modul;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ModuleDAO modulDAO = new ModuleDAO(this);
        ArrayList<Modul> moduls = modulDAO.all();
        ModulListAdapter modulListAdapter = new ModulListAdapter(moduls, this);
        modulListAdapter.addAll(moduls);
        ListView taskList = findViewById(R.id.task_list);
        taskList.setAdapter(modulListAdapter);
    }
}