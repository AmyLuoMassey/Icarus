package project.com.icarus.ui.task;

import static project.com.icarus.helpers.Helpers.fromDate;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Date;

import project.com.icarus.R;
import project.com.icarus.db.DatabaseHandler;
import project.com.icarus.db.Task;
import project.com.icarus.db.User;
import project.com.icarus.helpers.Helpers;

public class TaskFragment extends Fragment {


    ListView taskListView;
    Spinner spnUser;
    Button btnAddTask;
    Button btnCalendar, btnTaskConfirm, btnTaskCancel, btnCalendarSelect;
    EditText txtTaskName,  txtTaskDesc, txtTaskDate;
    TaskAdapter taskAdapter;
    Date schDate;
    int currentId;

    ArrayList<Task> taskList;
    ArrayList<Integer> userIdList;
    ArrayList<String> userNameList;

    PopupWindow popupAddTaskWindow,  popupEditTaskWindow, popupCalendarWindow;
    View popupAddTaskView,popupEditTaskView, popupCalendarView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_task, container, false);



        DatabaseHandler dbHandler = new DatabaseHandler(getContext(), null, null, 1);
        taskList = dbHandler.loadTasks();

        taskListView = root.findViewById(R.id.taskListView);
        taskAdapter = new TaskAdapter(getContext(), taskList);
        taskListView.setAdapter(taskAdapter);

        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task task = (Task)parent.getItemAtPosition(position);
                popupEditTaskView = inflater.inflate(R.layout.fragment_edit_task, null);



                // create a popup window
                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.MATCH_PARENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                popupEditTaskWindow = new PopupWindow(popupEditTaskView, width, height, focusable);
                popupEditTaskWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                btnTaskConfirm = (Button) popupEditTaskView.findViewById(R.id.btnTaskConfirm);
                btnTaskCancel = (Button) popupEditTaskView.findViewById(R.id.btnTaskCancel);
                btnCalendar = (Button) popupEditTaskView.findViewById(R.id.btnCalendar);

                txtTaskName = (EditText)  popupEditTaskView.findViewById(R.id.txt_taskName);

                txtTaskDesc = (EditText)  popupEditTaskView.findViewById(R.id.txt_taskDescription);
                txtTaskDate  =(EditText)  popupEditTaskView.findViewById(R.id.txt_scheduleDate);

                txtTaskName.setText(task.getTaskName());


                txtTaskDate.setText(fromDate(task.getScheduledDate()));
                txtTaskDesc.setText(task.getDescription());
                currentId = task.getTaskID();

                populateUserSpinner(popupEditTaskView);

                setupCalendarWindow(width, height, focusable, view, inflater);


                btnTaskConfirm.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View popupView) {

                        if (!txtTaskName.getText().equals("")){
                            DatabaseHandler dbHandler = new DatabaseHandler(getContext(), null, null, 1);


                            Task task = new Task(txtTaskName.getText().toString(),Helpers.toDate(txtTaskDate.getText().toString()),txtTaskDesc.getText().toString(),userIdList.get(spnUser.getSelectedItemPosition()),"",0);
                            task.setTaskID(currentId);
                            boolean result = dbHandler.updateTask(task);
                            if(result){
                                reloadAllData();
                                Toast.makeText(getContext(), "Task edited successfully", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(getContext(), "Task editing failed. Please contact your administrator.", Toast.LENGTH_LONG).show();
                            }
                            popupEditTaskWindow.dismiss();

                        }
                        else {
                            Toast.makeText(getContext(), "Please enter Task Name.", Toast.LENGTH_LONG).show();
                        }



                    }
                });
                btnTaskCancel.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View popupView) {
                        popupEditTaskWindow.dismiss();
                    }
                });


            }
        });



        btnAddTask = (Button) root.findViewById(R.id.btnAddTask);
        btnAddTask.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // inflate the layout of the popup window

                popupAddTaskView = inflater.inflate(R.layout.fragment_add_task, null);


                // create a popup window
                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.MATCH_PARENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                popupAddTaskWindow = new PopupWindow(popupAddTaskView, width, height, focusable);

                popupAddTaskWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                btnTaskConfirm = (Button) popupAddTaskView.findViewById(R.id.btnTaskConfirm);
                btnTaskCancel = (Button) popupAddTaskView.findViewById(R.id.btnTaskCancel);
                btnCalendar = (Button) popupAddTaskView.findViewById(R.id.btnCalendar);

                txtTaskName = (EditText)  popupAddTaskView.findViewById(R.id.txt_taskName);

                txtTaskDesc = (EditText)  popupAddTaskView.findViewById(R.id.txt_taskDescription);
                txtTaskDate  =(EditText)  popupAddTaskView.findViewById(R.id.txt_scheduleDate);

                populateUserSpinner(popupAddTaskView);
                setupCalendarWindow(width, height, focusable, v, inflater);


                btnTaskConfirm.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View popupView) {

                        if (!txtTaskName.getText().equals("")){
                            DatabaseHandler dbHandler = new DatabaseHandler(getContext(), null, null, 1);

                            Task task = new Task(txtTaskName.getText().toString(),Helpers.toDate(txtTaskDate.getText().toString()),txtTaskDesc.getText().toString(),userIdList.get(spnUser.getSelectedItemPosition()),"",0);

                            boolean result = dbHandler.addTask(task);
                            if(result){
                                reloadAllData();
                                Toast.makeText(getContext(), "Item added successfully", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(getContext(), "Item creation failed. Please contact your administrator.", Toast.LENGTH_LONG).show();
                            }
                            popupAddTaskWindow.dismiss();

                        }
                        else {
                              Toast.makeText(getContext(), "Please enter Task Name.", Toast.LENGTH_LONG).show();
                        }



                    }
                });
                btnTaskCancel.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View popupView) {
                        popupAddTaskWindow.dismiss();
                    }
                });

            }
        });



        return root;
    }
    public void setupCalendarWindow(int width, int height, boolean focusable, View view,  LayoutInflater inflater){
        btnCalendar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View popupView) {
                if(popupCalendarWindow !=null){

                    popupCalendarWindow = new PopupWindow(popupCalendarView, width, height, focusable);
                    popupCalendarWindow.showAtLocation(popupCalendarView, Gravity.CENTER, 0, 0);

                }
                else {
                    popupCalendarView = inflater.inflate(R.layout.fragment_calendar, null);

                    popupCalendarWindow = new PopupWindow(popupCalendarView, width, height, focusable);
                    popupCalendarWindow.showAtLocation(popupCalendarView, Gravity.CENTER, 0, 0);

                }

                CalendarView calendarView=(CalendarView) popupCalendarView.findViewById(R.id.calendarView);
                calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

                    @Override
                    public void onSelectedDayChange(CalendarView view, int year, int month,
                                                    int dayOfMonth) {
                         schDate = new Date(year-1900,month,dayOfMonth);

                    }
                });
                btnCalendarSelect = (Button) popupCalendarView.findViewById(R.id.btnCalendarSelect);
                btnCalendarSelect.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View popupView) {
                        txtTaskDate.setText(fromDate(schDate));
                        popupCalendarWindow.dismiss();
                    }
                });


            }
        });
    }

    private void populateUserSpinner(View view){
        spnUser = (Spinner) view.findViewById(R.id.dropdown_user);

        DatabaseHandler dbHandler = new DatabaseHandler(getContext(), null, null, 1);
        ArrayList<User> userList = dbHandler.loadUsers();
        userIdList = new ArrayList<>();
        userNameList = new ArrayList<>();

        for (User usr : userList) {
            userNameList.add(usr.getUserName());
            userIdList.add(usr.getUserID());

        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, userNameList);
        spnUser.setAdapter(adapter);

    }



    private void reloadAllData(){
        // get new modified random data
        DatabaseHandler dbHandler = new DatabaseHandler(getContext(), null, null, 1);
        taskList = dbHandler.loadTasks();

        // update data in adapter
        taskAdapter.getTaskData().clear();
        taskAdapter.getTaskData().addAll(taskList);
        taskAdapter.notifyDataSetChanged();
    }



}
