package com.example.todoapp;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginUserTest";
    EditText name, password;
    Button signup, login;
    List<EUser> userList;
    private UserViewModel userViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        signup = findViewById(R.id.login_activity_btn_signup);
        login = findViewById(R.id.login_activity_btn_login);
        name = findViewById(R.id.login_activity_et_name);
        password = findViewById(R.id.login_activity_et_password);

        SharedPreferences preference = getApplicationContext().getSharedPreferences("todo_pref",  0);
        SharedPreferences.Editor editor = preference.edit();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userList = userViewModel.getAllUsers();

                for (int i = 0; i< userList.size(); i++) {

                    Log.d(TAG, userList.get(i).getName());
                    //if username and password matches the database.
                    if(userList.get(i).getName().equalsIgnoreCase(name.getText().toString())
                            && userList.get(i).getPassword().equals(password.getText().toString())) {

                        editor.putBoolean("authentication",true);
                        editor.putInt("user_id", userList.get(i).getUser_id());
                        editor.commit();

                    }
                }

                Boolean authentication = preference.getBoolean("authentication",false);

                if(authentication) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    name.setError("Username or password doesn't match!");
                    Toast.makeText(LoginActivity.this,"User not found!", Toast.LENGTH_LONG).show();
                }



            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });


    }
}
