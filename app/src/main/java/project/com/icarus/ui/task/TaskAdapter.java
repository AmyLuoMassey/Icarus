package project.com.icarus.ui.task;

import static project.com.icarus.helpers.Helpers.fromDate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import project.com.icarus.R;
import project.com.icarus.db.Task;


public class TaskAdapter extends ArrayAdapter<Task> {

    private ArrayList<Task> _taskArrayList;

    public TaskAdapter(@NonNull Context context, ArrayList<Task> taskArrayList) {
        super(context, 0, taskArrayList);
        _taskArrayList = taskArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listTaskView = convertView;
        if (listTaskView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listTaskView = LayoutInflater.from(getContext()).inflate(R.layout.schedule_listview, parent, false);
        }
        Task task = getItem(position);
        TextView identifier = listTaskView.findViewById(R.id.txtTaskIdentifier);
        TextView name = listTaskView.findViewById(R.id.txtTaskName);
        TextView date = listTaskView.findViewById(R.id.txtTaskDate);
        Integer id = (Integer)task.getTaskID();
        identifier.setText(id.toString());
        name.setText(task.getTaskName());
        date.setText(fromDate(task.getScheduledDate()));

        return listTaskView;
    }


    public ArrayList<Task> getTaskData() {
        return _taskArrayList;
    }
}