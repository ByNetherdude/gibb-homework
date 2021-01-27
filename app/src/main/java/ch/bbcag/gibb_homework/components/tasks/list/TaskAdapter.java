package ch.bbcag.gibb_homework.components.tasks.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import ch.bbcag.gibb_homework.R;
import ch.bbcag.gibb_homework.model.Task;

public class TaskAdapter extends ArrayAdapter<Task> {
    private ArrayList<Task> objects;

    public TaskAdapter(Context ctx, int textViewResourceId, ArrayList<Task> objects) {
        super(ctx, textViewResourceId, objects);
        this.objects = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v==null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout., null);
        }
    }
}
