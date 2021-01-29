package ch.bbcag.gibb_homework.components.tasks.list;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ch.bbcag.gibb_homework.R;
import ch.bbcag.gibb_homework.model.Task;

public class TaskAdapter extends ArrayAdapter<Task> {
    private ArrayList<Task> dataSet = new ArrayList<Task>();
    Context ctx;

    private static class ViewHolder {
        TextView txtTitle;
        TextView txtDueDate;
        TextView txtModule;
    }

    public TaskAdapter(ArrayList<Task> data, Context ctx) {
        super(ctx, R.layout.task_list_item, data);
        this.dataSet = data;
        this.ctx = ctx;
    }

//    @Override
//    public void onClick(View view) {
//        int position = (Integer) view.getTag();
//        Object object = getItem(position);
//        Task task = (Task)object;
//
//        // navigate to detail view
//    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Task task = getItem(position);

        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.task_list_item, parent, false);
            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.title);
            viewHolder.txtDueDate = (TextView) convertView.findViewById(R.id.dueDate);
            viewHolder.txtModule = (TextView) convertView.findViewById(R.id.taskModuleTag);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Drawable drawable = getContext().getResources().getDrawable(R.drawable.rounded_edge);
        drawable.setColorFilter(Color.parseColor(task.getModuleColor()), PorterDuff.Mode.SRC_ATOP);
        LinearLayout ll = (LinearLayout) convertView.findViewById(R.id.taskModuleTagBg);
        ll.setBackground(drawable);

        lastPosition = position;

        viewHolder.txtTitle.setText(task.getTitle());
        viewHolder.txtDueDate.setText(task.getDueDate());
        viewHolder.txtModule.setText(task.getRelatedModuleNumber());

        return convertView;
    }
}
