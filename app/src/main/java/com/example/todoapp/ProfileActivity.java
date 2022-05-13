package com.example.todoapp;
//import required Library

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

//create a class ProfileActivity that inherit the property of AppCompatActivity
public class ProfileActivity extends AppCompatActivity {
    //initializing the values in Tag variable
    private static final String TAG = "ProfileTest";
    //Declare the required view component
    private TodoViewModel todoViewModel;
    UserViewModel userViewModel;
    Integer user_id;
    TextView name, old_pass, new_pass;
    Button submit, delete;

    /**
     * Initializes the activity.
     *
     * @param savedInstanceState The current state data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //super keyword is used to refer parent class object
        super.onCreate(savedInstanceState);
        //it set the XML file as your main layout when the app starts
        setContentView(R.layout.activity_profile);
        //ViewModelProvider call our primary constructor of ViewModel and create the instance of
        // TodoViewModel and give the instance back
        todoViewModel = new ViewModelProvider(this).get(TodoViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        //get the required view component by id
        name = findViewById(R.id.profile_activity_tv_name);
        old_pass = findViewById(R.id.profile_activity_tv_oldpass);
        new_pass = findViewById(R.id.profile_activity_tv_newpass);
        submit = findViewById(R.id.profile_activity_btn_submit);
        delete = findViewById(R.id.profile_activity_btn_delete);
        //SharedPreferences store and retrieve small amounts of primitive data as key/value pairs
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("todo_pref", 0);
        user_id = preferences.getInt("user_id", -1);
        //access the userViewModel from EUser class
        EUser eUser = userViewModel.getUserById(user_id);
        //set the text in name by getting from eUser
        name.setText(eUser.getName());
        // Create an anonymous implementation of OnClickListener for submit button
        submit.setOnClickListener(new View.OnClickListener() {
            /**
             * click event is done
             * @param v
             */
            @Override
            public void onClick(View v) {
                //condition that check whether old_password filed is empty
                if (old_pass.getText().toString().trim().toString().equals("") ||
                        old_pass.getText().toString().trim().toString().equals("")) {
                    //display the toast message
                    Toast.makeText(ProfileActivity.this, "Password field is empty!", Toast.LENGTH_SHORT).show();
                } else {
                    //condition to check whether the old_password taken from input field
                    if (old_pass.getText().toString().equals(eUser.getPassword())) {
                        eUser.setPassword(new_pass.getText().toString());
                        userViewModel.update(eUser);
                        //display toast message after success
                        Toast.makeText(ProfileActivity.this, "Password updated!", Toast.LENGTH_SHORT).show();
                    }
                    //otherwise Password is incorrect message will display
                    else old_pass.setError("Password is incorrect!");
                }
            }
        });
        // Create an anonymous implementation of OnClickListener for delete button
        delete.setOnClickListener(new View.OnClickListener() {
            /**
             * click event is done
             * @param v
             */
            @Override
            public void onClick(View v) {
                //creating AlertDialog.Builder in view after clicking delete button
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ProfileActivity.this);
                //message display in alertDialog
                alertDialog.setMessage(getString(R.string.alert_delete))
                        .setTitle(getString(R.string.app_name))
                        .setIcon(R.mipmap.ic_launcher)
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            //deletes the user, clears preferences and goes to login page.
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //deleteing the todos associated with this user.
                                List<ETodo> eTodo = todoViewModel.getAll();
                                //display in console
                                Log.d(TAG, "user_id: " + user_id + "size of etodo" + eTodo.size());
                                //for loop to display all the data from eTodo
                                for (int i = 0; i < eTodo.size(); i++) {
                                    //display in console
                                    Log.d(TAG, "todo_id: " + eTodo.get(i).getUser_id() + " Index value: " + i + " size of etodo" + eTodo.size() + "******");
                                    //if the eTodo user id is equal to present user id in db
                                    if (eTodo.get(i).getUser_id() == user_id) {
                                        //display in console
                                        Log.d(TAG, "todo_id: " + eTodo.get(i).getUser_id() + " size of etodo" + eTodo.size() + "******");
                                        //delete all the data input by given user
                                        todoViewModel.deleteById(eTodo.get(i));
                                    }
                                }
                                //deleting the data from userViewModel
                                userViewModel.deleteById(eUser);
                                //SharedPreferences.Editor, Returns a reference to the same Editor
                                // object, so you can chain put calls together
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.clear();
                                editor.commit();
                                //creating the instance of intent for redirection
                                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                                //activity starts
                                startActivity(intent);
                            }
                        })

                        .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                alertDialog.show();

            }
        });


    }

}
