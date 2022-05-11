package com.example.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "UserTest";
    EditText name, password, confirmPassword;
    Button signup, login;
    List<EUser> userList;
    private UserViewModel userViewModel;
    Boolean error = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        signup = findViewById(R.id.login_activity_btn_login);
        login = findViewById(R.id.signup_activity_btn_login);
        name = findViewById(R.id.login_activity_et_name);
        password = findViewById(R.id.login_activity_et_password);
        confirmPassword = findViewById(R.id.signup_activity_et_confirm_pass);

        signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                error = false;
                userList = userViewModel.getAllUsers();
                EUser eUser = new EUser();
                eUser.setUser_id(userList.size() + 1);
                eUser.setName(name.getText().toString());
                eUser.setPassword(password.getText().toString());

                if(password.getText().toString().trim().equals("") ||
                        confirmPassword.getText().toString().trim().equals("")) {
                    error = true;
                    Toast.makeText(SignupActivity.this, "Password field shouldn't be empty!", Toast.LENGTH_SHORT).show();
                }
                //validation for confirm password and password match
                if(!password.getText().toString().equals(confirmPassword.getText().toString())) {
                    error = true;
                    password.setError("Password must match confirm password!");
                }
                //validation for unique username
                for (int i = 0; i< userList.size(); i++) {
                    if(name.getText().toString().equalsIgnoreCase(userList.get(i).getName())) {
                        Log.d(TAG, userList.get(i).getName());
                        name.setError("User name already exists!");
                        error = true;
                        break;
                    }

                }


                if(!error) {
                    userViewModel.insert(eUser);
                    Toast.makeText(SignupActivity.this,"Registered successfully!", Toast.LENGTH_LONG).show();
                    error = false;
                }
                else {
                    Toast.makeText(SignupActivity.this,"Registration Failed!", Toast.LENGTH_LONG).show();
                }

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
