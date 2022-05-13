package com.example.todoapp;
//import required Library

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

//EditActivity class inherit the property of AppCompatActivity class
public class EditActivity extends AppCompatActivity {
    /**
     * FragmentManager is the class that perform action of app like adding,removing, and replacing them back stack.
     * Declaring the variable if Fragment and FragmentManager class
     */
    FragmentManager fragmentManager;
    Fragment fragment;

    /**
     * @param savedInstanceState Activities have the ability,to restore themselves to a previous state using the data stored in this bundle
     * @Override annotation indicates that the EditActivity class method is over-writing its AppCompatActivity class method
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //super keyword is used to refer parent class object
        super.onCreate(savedInstanceState);
        //it set the XML file as your main layout when page is access
        setContentView(R.layout.activity_edit);
        //it give the SupportFragmentManager or FragmentManager based on which Fragment class you have extended
        fragmentManager = getSupportFragmentManager();
        //creating the object of EditTodoFragment class
        fragment = new EditTodoFragment();
        // replace a fragment with another, you should have added them dynamically and Commit the transaction
        fragmentManager.beginTransaction().replace(R.id.edit_fragment_container, fragment).commit();
    }
}
