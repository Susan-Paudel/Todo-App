package com.example.todoapp;
//import required Library

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//EditTodoFragment inherit the property of Fragment class
public class EditTodoFragment extends Fragment {
    //Declaring the required interface
    View rootView;
    EditText txtTitle, txtDescription, txtDate;
    RadioGroup rgPriority;
    Button btnSave, btnCancel;
    CheckBox chComplete;
    Boolean error;
    //initializing the variable in private static final Tag
    private static final String TAG = "TodoTest";
    //int todoId
    int todoId;
    //initializing the different int value in different priority
    public static final int HIGH_PRIORITY = 1;
    public static final int MEDIUM_PRIORITY = 2;
    public static final int LOW_PRIORITY = 3;

    /**
     * Inflate fragment layout, selects recycle view
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_edit_todo, container, false);
        //getting the required views by id
        txtTitle = rootView.findViewById(R.id.edit_fragment_txt_name);
        txtDescription = rootView.findViewById(R.id.edit_fragment_txt_description);
        txtDate = rootView.findViewById(R.id.edit_fragment_txt_date);
        rgPriority = rootView.findViewById(R.id.edit_fragment_rg_priority);
        chComplete = rootView.findViewById(R.id.edit_fragment_chk_complete);
        btnSave = rootView.findViewById(R.id.edit_fragment_btn_save);
        btnCancel = rootView.findViewById(R.id.edit_fragment_btn_cancel);

        loadUpdateData();
        // Create an anonymous implementation of OnTouchListener for txtDate
        txtDate.setOnTouchListener(new View.OnTouchListener() {
            /**
             * when touch event is done from view with motionEvent event object
             * @param v
             * @param event
             * @return false
             */
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //if event action is equal to motion event
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    //display test date function
                    DisplayTestDate();
                return false;

            }
        });
        // Create an anonymous implementation of OnClickListener for btnSave
        btnSave.setOnClickListener(new View.OnClickListener() {
            /**
             * if the event is clicked SaveTodo function is called
             * @param v
             */
            @Override
            public void onClick(View v) {
                SaveTodo();
            }
        });
        //Create an anonymous implementation of OnClickListener for btnCancel
        btnCancel.setOnClickListener(new View.OnClickListener() {
            /**
             * if onclick event takes action showAlertCancel function is called
             * @param v
             */
            @Override
            public void onClick(View v) {
                ShowAlertCancel();
            }
        });
        //return rootView
        return rootView;
    }

    //void function DisplayTestDate does not return anything simple a function
    void DisplayTestDate() {
        //creating the object of calendar
        Calendar calendar = Calendar.getInstance();
        //getting the different time according to day,month and year
        int cDay = calendar.get(Calendar.DAY_OF_MONTH);
        int cMonth = calendar.get(Calendar.MONTH);
        int cYear = calendar.get(Calendar.YEAR);
        /**
         * creating the object of name pickerDialog of DatePickerDialog class
         * Create an anonymous implementation of OnDateSetListener for formatting the date
         */
        DatePickerDialog pickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            /**
             * when onDateSet is clicked
             * @param view
             * @param year
             * @param month
             * @param dayOfMonth
             */
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //set the txtDate view in this way
                txtDate.setText(year + "-" + month + "-" + dayOfMonth);
            }
        }, cYear, cMonth, cDay);
        pickerDialog.show();
    }

    //showAlertCancel function
    void ShowAlertCancel() {
        //alertDialog is created of AlertDialog.Builder class
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        //some message display after some event
        //Create an anonymous implementation of OnClickListener for alertDialog object
        alertDialog.setMessage(getString(R.string.alert_cancel))
                .setTitle(getString(R.string.app_name))
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    /**
                     * when the onclick event happen
                     * @param dialog
                     * @param which
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //creating the object of Intent
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        //redirect to next page
                        startActivity(intent);
                    }
                })
                //Create an anonymous implementation of OnClickListener
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        alertDialog.show();
    }

    //function loadUpdateDate
    void loadUpdateData() {
        //declaring the variable todoId
        todoId = getActivity().getIntent().getIntExtra("TodoId", -1);
        //creating the object TodoViewModel class
        TodoViewModel viewModel = new ViewModelProvider(this).get(TodoViewModel.class);
        //if todoId is not equal to negative one
        if (todoId != -1) {
            //btnSave view setText to Update
            btnSave.setText("Update");
            //passing the id into ETodo class
            ETodo todo = viewModel.getTodoById(todoId);
            //textTile setText the title in it
            txtTitle.setText(todo.getTitle());
            //description is set
            txtDescription.setText(todo.getDescription());
            //formatting the date
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            //set the formatted date
            txtDate.setText(format.format(todo.getTodoDate()));
            //switch case
            switch (todo.getPriority()) {
                case 1:
                    //edit_fragment_rb_high component will display
                    rgPriority.check(R.id.edit_fragment_rb_high);
                    break;
                case 2:
                    //edit_fragment_rb_medium component will display
                    rgPriority.check(R.id.edit_fragment_rb_medium);
                    break;
                case 3:
                    //edit_fragment_rb_low component will display
                    rgPriority.check(R.id.edit_fragment_rb_low);
                    break;
            }
            chComplete.setChecked(todo.isCompleted());
        }
    }

    //function SaveTodo
    void SaveTodo() {
        //error is set to false
        error = false;
        //creating the object of ETodo and Date
        ETodo eTodo = new ETodo();
        Date todoDate = new Date();
        //initailizing the values to checkedPriority and priority
        int checkedPriority = -1;
        int priority = 0;
        //try start
        try {
            //format tge date and set to todoDate
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            todoDate = format.parse(txtDate.getText().toString());
        }//catch block will execute if the try block does not get execute
        catch (ParseException ex) {
            ex.printStackTrace();
            //shows Invalid date
            txtDate.setError("Invalid date!");
        }
        //set the rgPriority
        checkedPriority = rgPriority.getCheckedRadioButtonId();
        //switch statement checkedPriority exist
        switch (checkedPriority) {
            case R.id.edit_fragment_rb_high:
                //set to high priority
                priority = HIGH_PRIORITY;
                break;
            case R.id.edit_fragment_rb_medium:
                //set to medium priority
                priority = MEDIUM_PRIORITY;
                break;
            case R.id.edit_fragment_rb_low:
                //set to low priority
                priority = LOW_PRIORITY;
                break;
        }
        //SharedPreferences store and retrieve small amounts of primitive data as key/value pairs
        SharedPreferences preferences = getContext().getSharedPreferences("todo_pref", 0);
        int user_id = preferences.getInt("user_id", 0);
        //print in the console
        Log.d(TAG, "*** user_id: " + user_id + " ****");
        //if condition input type EditText is empty i.e.validation then toast is displayed
        if (txtTitle.getText().toString().trim().equals("") || txtDescription.getText().toString().trim().equals("")
                || txtDate.getText().toString().trim().equals("") || checkedPriority == -1) {
            Toast.makeText(getContext(), "Fill all the text fields and select priority!", Toast.LENGTH_SHORT).show();
            error = true;
        } else {
            //get the data from all the fields
            eTodo.setTitle(txtTitle.getText().toString());
            eTodo.setDescription(txtDescription.getText().toString());
            eTodo.setTodoDate(todoDate);
            eTodo.setPriority(priority);
            eTodo.setCompleted(chComplete.isChecked());
            eTodo.setUser_id(user_id);
        }
        //creating the object of TodoViewModel
        TodoViewModel viewModel = new ViewModelProvider(this).get(TodoViewModel.class);
        //if error not exist
        if (!error) {
            //if todoId is not equal to -1
            if (todoId != -1) {
                //setId to eTodo
                eTodo.setId(todoId);
                //viewModel is update else insert
                viewModel.update(eTodo);
            } else viewModel.insert(eTodo);

            //Toast message shows TodoSaved with short length
            Toast.makeText(getActivity(), "Todo Saved", Toast.LENGTH_SHORT).show();
            //intent object is created
            Intent intent = new Intent(getActivity(), MainActivity.class);
            //redirect to next page
            startActivity(intent);
        }


    }
}
