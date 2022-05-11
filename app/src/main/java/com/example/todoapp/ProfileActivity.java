package com.example.todoapp;

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

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileTest";
    private TodoViewModel todoViewModel;
    UserViewModel userViewModel;
    Integer user_id;
    TextView name, old_pass, new_pass;
    Button submit, delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        todoViewModel = new ViewModelProvider(this).get(TodoViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        name = findViewById(R.id.profile_activity_tv_name);
        old_pass = findViewById(R.id.profile_activity_tv_oldpass);
        new_pass = findViewById(R.id.profile_activity_tv_newpass);
        submit = findViewById(R.id.profile_activity_btn_submit);
        delete = findViewById(R.id.profile_activity_btn_delete);

        SharedPreferences preferences=getApplicationContext().getSharedPreferences("todo_pref",0);
        user_id = preferences.getInt("user_id",-1);

        EUser eUser = userViewModel.getUserById(user_id);

        name.setText(eUser.getName());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(old_pass.getText().toString().trim().toString().equals("") ||
                        old_pass.getText().toString().trim().toString().equals("")) {
                    Toast.makeText(ProfileActivity.this, "Password field is empty!", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(old_pass.getText().toString().equals(eUser.getPassword())) {
                        eUser.setPassword(new_pass.getText().toString());
                        userViewModel.update(eUser);
                        Toast.makeText(ProfileActivity.this, "Password updated!", Toast.LENGTH_SHORT).show();
                    }
                    else old_pass.setError("Password is incorrect!");
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ProfileActivity.this);
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
                                Log.d(TAG, "user_id: " + user_id + "size of etodo" + eTodo.size());
                                for(int i = 0; i<eTodo.size(); i++) {
                                    Log.d(TAG, "todo_id: " + eTodo.get(i).getUser_id() + " Index value: "+ i +" size of etodo" + eTodo.size() + "******");
                                    if(eTodo.get(i).getUser_id() == user_id) {
                                        Log.d(TAG, "todo_id: " + eTodo.get(i).getUser_id() +" size of etodo" + eTodo.size() + "******");
                                        todoViewModel.deleteById(eTodo.get(i));
                                    }
                                }
                                userViewModel.deleteById(eUser);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.clear();
                                editor.commit();
                                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
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
