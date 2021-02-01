package ch.bbcag.gibb_homework.components.modul.list;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import ch.bbcag.gibb_homework.DetailActivity;
import ch.bbcag.gibb_homework.R;
import ch.bbcag.gibb_homework.components.tasks.list.TaskAdapter;
import ch.bbcag.gibb_homework.constants.IntentContext;
import ch.bbcag.gibb_homework.model.Modul;
import ch.bbcag.gibb_homework.model.Task;

public class ModulListAdapter extends ArrayAdapter<Modul> {
    private ArrayList<Modul> dataSet = new ArrayList<Modul>();
    Context ctx;

    private static class ViewHolder {
        TextView txtTitle;
        TextView txtNumber;
        CheckBox intIsActive;
    }

    public ModulListAdapter(ArrayList<Modul> data, Context ctx) {
        super(ctx, R.layout.settings_modul_list_item, data);
        this.dataSet = data;
        this.ctx = ctx;
    }

    private int lastPosition = -1;

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Modul modul = getItem(position);

        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.settings_modul_list_item, parent, false);
            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.title);
            viewHolder.txtNumber = (TextView) convertView.findViewById(R.id.modulNumber);
            viewHolder.intIsActive = (CheckBox) convertView.findViewById(R.id.isActive);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ModulListAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }

        lastPosition = position;

        viewHolder.txtTitle.setText(modul.getTitle());
        viewHolder.txtNumber.setText(modul.getNumber());
        viewHolder.intIsActive.setChecked();

        return convertView;
    }
}
