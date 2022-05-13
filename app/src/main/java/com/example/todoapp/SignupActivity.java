package com.example.todoapp;
//import the required Library

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

//create class SignupActivity that extends AppCompactActivity
public class SignupActivity extends AppCompatActivity {
    //initializing the values in Tag variable
    private static final String TAG = "UserTest";
    //Declare the required view component
    EditText name, password, confirmPassword;
    Button signup, login;
    List<EUser> userList;
    //Declare UserViewModel class
    private UserViewModel userViewModel;
    //setting boolean error false
    Boolean error = false;

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
        setContentView(R.layout.activity_signup);
        //ViewModelProvider call our primary constructor of ViewModel and create the instance of
        // TodoViewModel and give the instance back
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        //get the required view component by id
        signup = findViewById(R.id.login_activity_btn_login);
        login = findViewById(R.id.signup_activity_btn_login);
        name = findViewById(R.id.login_activity_et_name);
        password = findViewById(R.id.login_activity_et_password);
        confirmPassword = findViewById(R.id.signup_activity_et_confirm_pass);
        //Create an anonymous implementation of OnClickListener for signup button
        signup.setOnClickListener(new View.OnClickListener() {
            /**
             * when clicked event is called
             * @param v
             */
            @Override
            public void onClick(View v) {
                //error is false
                error = false;
                //initializing the users from userViewModel into userList
                userList = userViewModel.getAllUsers();
                //creating object of EUser class
                EUser eUser = new EUser();
                //in every registration user id increased
                eUser.setUser_id(userList.size() + 1);
                //get name from input filed and set name in to db
                eUser.setName(name.getText().toString());
                //get password from input filed and set password in to db
                eUser.setPassword(password.getText().toString());
                //password field validation if empty
                if (password.getText().toString().trim().equals("") ||
                        confirmPassword.getText().toString().trim().equals("")) {
                    //error variable is set to true
                    error = true;
                    //Toast message will display
                    Toast.makeText(SignupActivity.this, "Password field shouldn't be empty!", Toast.LENGTH_SHORT).show();
                }
                //validation for confirm password and password match
                if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
                    //error exist the it will set to true
                    error = true;
                    //error message showing confirm password must match
                    password.setError("Password must match confirm password!");
                }
                //validation for unique username
                for (int i = 0; i < userList.size(); i++) {
                    if (name.getText().toString().equalsIgnoreCase(userList.get(i).getName())) {
                        Log.d(TAG, userList.get(i).getName());
                        //if user exists
                        name.setError("User name already exists!");
                        //error is set to true
                        error = true;
                        break;
                    }

                }

                //if error does not exists
                if (!error) {
                    //data inserted
                    userViewModel.insert(eUser);
                    //success message
                    Toast.makeText(SignupActivity.this, "Registered successfully!", Toast.LENGTH_LONG).show();
                    error = false;
                } else {
                    //unsuccessful registration error
                    Toast.makeText(SignupActivity.this, "Registration Failed!", Toast.LENGTH_LONG).show();
                }

            }
        });
        //Create an anonymous implementation of OnClickListener for login button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creating the intent instance
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                //activity starts
                startActivity(intent);
            }
        });
    }
}
