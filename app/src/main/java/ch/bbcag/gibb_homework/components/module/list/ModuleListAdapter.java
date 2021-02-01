package ch.bbcag.gibb_homework.components.module.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;


import java.util.ArrayList;

import ch.bbcag.gibb_homework.R;
import ch.bbcag.gibb_homework.model.Module;

public class ModuleListAdapter extends ArrayAdapter<Module> {
    private ArrayList<Module> dataSet = new ArrayList<Module>();
    Context ctx;

    private static class ViewHolder {
        TextView txtTitle;
        TextView txtNumber;
        CheckBox intIsActive;
    }

    public ModuleListAdapter(ArrayList<Module> data, Context ctx) {
        super(ctx, R.layout.settings_module_list_item, data);
        this.dataSet = data;
        this.ctx = ctx;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Module module = getItem(position);

        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.settings_module_list_item, parent, false);
            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.title);
            viewHolder.txtNumber = (TextView) convertView.findViewById(R.id.moduleNumber);
            viewHolder.intIsActive = (CheckBox) convertView.findViewById(R.id.isActive);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ModuleListAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }

        lastPosition = position;

        viewHolder.txtTitle.setText(module.getTitle());
        viewHolder.txtNumber.setText(module.getNumber());
        viewHolder.intIsActive.setChecked(module.isActive());

        return convertView;
    }
}
