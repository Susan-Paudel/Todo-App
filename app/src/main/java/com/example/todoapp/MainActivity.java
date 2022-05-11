package com.example.todoapp;

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

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "UserList";
    private TodoViewModel todoViewModel;
    private UserViewModel userViewModel;

    FragmentManager fragmentManager;
    Fragment fragment;

    SharedPreferences preferences;

    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        todoViewModel = new ViewModelProvider(this).get(TodoViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        preferences = getApplicationContext().getSharedPreferences("todo_pref", 0);
        int user_id = preferences.getInt("user_id",0);
        EUser eUser = userViewModel.getUserById(user_id);

        Toolbar toolbar = findViewById(R.id.toolbar);
        //setWindowActionBar(false);
        setSupportActionBar(toolbar);
        TextView toolbarTitle = (TextView) findViewById(R.id.toolbarTitle);
        toolbarTitle.setText(eUser.getName());
        toolbarTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        getSupportActionBar().setIcon(R.drawable.ic_account);

        List<EUser> users = userViewModel.getAllUsers();

        for (EUser user : users) {
            Log.d(TAG, "onCreate: " + user.getName());
        }

        floatingActionButton = findViewById(R.id.btn_activity_main_floating);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });
        fragmentManager = getSupportFragmentManager();
        fragment = new ListTodoFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.list_todo_container, fragment)
                .commit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int user_id = preferences.getInt("user_id",-1);
        switch (item.getItemId()){
            case R.id.mnu_delete_all:
                todoViewModel.deleteAll(user_id);
                Toast.makeText(getApplicationContext(),"All todos deleted!", Toast.LENGTH_LONG).show();
                break;
            case R.id.mnu_delete_completed:

                if(user_id != -1) {
                    todoViewModel.deleteAllCompleted(user_id, true);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Failed to delete!", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.mnu_logout:

                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                Intent intent= new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }}