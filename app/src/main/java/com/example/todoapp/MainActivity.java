package com.example.todoapp;
//import required Library

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

//MainActivity as child class that extends AppCompatActivity parent class
public class MainActivity extends AppCompatActivity {
    //Initialized the string value in TAG
    private static final String TAG = "UserList";
    //Declare the required classes
    private TodoViewModel todoViewModel;
    private UserViewModel userViewModel;
    //Declare FragmentManager class responsible for performing actions on your app's fragments
    FragmentManager fragmentManager;
    Fragment fragment;
    //Declare Interface for accessing and modifying preference data returned by Context.getSharedPreferences(String, int)
    SharedPreferences preferences;
    //Declare FloatingActionButton is a circular button that triggers the primary action in your app's UI
    FloatingActionButton floatingActionButton;

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
        setContentView(R.layout.activity_main);


        //ViewModelProvider call our primary constructor of ViewModel and create the instance of
        // TodoViewModel and give the instance back
        todoViewModel = new ViewModelProvider(this).get(TodoViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        //SharedPreferences store and retrieve small amounts of primitive data as key/value pairs
        preferences = getApplicationContext().getSharedPreferences("todo_pref", 0);
        int user_id = preferences.getInt("user_id", 0);
        //access the userViewModel from EUser class
        EUser eUser = userViewModel.getUserById(user_id);
        //get toolbar using id
        Toolbar toolbar = findViewById(R.id.toolbar);
        //setWindowActionBar(false);
        setSupportActionBar(toolbar);
        //get textview by id
        TextView toolbarTitle = (TextView) findViewById(R.id.toolbarTitle);
        //set the textView
        toolbarTitle.setText(eUser.getName());
        // Create an anonymous implementation of OnClickListener for toolbarTitle
        toolbarTitle.setOnClickListener(new View.OnClickListener() {
            /**
             * if the toolbar title is clicked
             * @param v
             */
            @Override
            public void onClick(View v) {
                //creating object of intent for redirect to another page
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                //start activity
                startActivity(intent);
            }
        });
        //get the icon for toolbar title from drawable folder
        getSupportActionBar().setIcon(R.drawable.ic_account);
        //set all the values of userModel into users
        List<EUser> users = userViewModel.getAllUsers();
        //foreach to access each data or values from users
        for (EUser user : users) {
            //display message in console
            Log.d(TAG, "onCreate: " + user.getName());
        }
        //getting the floatingActionButton by it id
        floatingActionButton = findViewById(R.id.btn_activity_main_floating);
        // Create an anonymous implementation of OnClickListener for floatingActionButton
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creating the object of Intent
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                //activity start
                startActivity(intent);
            }
        });
        //assigning getSupportFragmentManager function in fragmentManager
        fragmentManager = getSupportFragmentManager();
        //accessing the list of fragments
        fragment = new ListTodoFragment();
        //fragments are replace by another activity
        //commit() call signals to the FragmentManager that all operations have been added to the transaction
        fragmentManager.beginTransaction()
                .replace(R.id.list_todo_container, fragment)
                .commit();


    }

    /**
     * onCreateOptionsMenu has been created to set the main_menu
     *
     * @param menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * creating onOptionItemSelected method is used to deleted all the user and todolist
     *
     * @param item
     * @return onOptionsItemSelected(item)
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //initialized the user_id from preferences where id is negative one
        int user_id = preferences.getInt("user_id", -1);
        //switch condition get id from item
        switch (item.getItemId()) {
            //in dropdown menu delete all button is clicked
            case R.id.mnu_delete_all:
                //delete all the user from todoViewModel
                todoViewModel.deleteAll(user_id);
                //toast message
                Toast.makeText(getApplicationContext(), "All todos deleted!", Toast.LENGTH_LONG).show();
                break;
            //in dropdown menu delete completed button is clicked
            case R.id.mnu_delete_completed:
                //if user_id is not equal to negative one
                if (user_id != -1) {
                    //user_id deleted
                    todoViewModel.deleteAllCompleted(user_id, true);
                }
                //else failed message will display
                else {
                    Toast.makeText(getApplicationContext(), "Failed to delete!", Toast.LENGTH_LONG).show();
                }

                break;
            //when logout is clicked then this case will run
            case R.id.mnu_logout:
                //clear all the SharedPreferences
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                //redirect to loginActivity page by the help of intent object
                Intent intent = new Intent(this, LoginActivity.class);
                //start activity
                startActivity(intent);
                //finish
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}