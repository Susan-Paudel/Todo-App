package com.example.todoapp;
//import the required Library

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

//create class LoginActivity that extends AppCompatActivity
public class LoginActivity extends AppCompatActivity {
    //initialized the string value in Tag variable
    private static final String TAG = "LoginUserTest";
    //Declare the view components
    EditText name, password;
    Button signup, login;
    List<EUser> userList;
    private UserViewModel userViewModel;

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
        setContentView(R.layout.activity_login);
        // create an Object of UserViewModel in an Activity
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        //getting the id values from view
        signup = findViewById(R.id.login_activity_btn_signup);
        login = findViewById(R.id.login_activity_btn_login);
        name = findViewById(R.id.login_activity_et_name);
        password = findViewById(R.id.login_activity_et_password);
        /**
         * preferences will automatically save to SharedPreferences as the user interacts with them.
         * To retrieve an instance of SharedPreferences that the preference hierarchy in this activity
         * will use, call getDefaultSharedPreferences(android.content.Context) with a context in the
         * same package as this activity
         */
        SharedPreferences preference = getApplicationContext().getSharedPreferences("todo_pref", 0);
        SharedPreferences.Editor editor = preference.edit();

        // Create an anonymous implementation of OnClickListener for login
        login.setOnClickListener(new View.OnClickListener() {
            /**
             * when click event is done
             * @param v
             */
            @Override
            public void onClick(View v) {
                //get all the data from userViewModel
                userList = userViewModel.getAllUsers();
                //for loop to fetch all the data exist in userList
                for (int i = 0; i < userList.size(); i++) {
                    //show details in console
                    Log.d(TAG, userList.get(i).getName());
                    //if username and password matches the database.
                    if (userList.get(i).getName().equalsIgnoreCase(name.getText().toString())
                            && userList.get(i).getPassword().equals(password.getText().toString())) {

                        editor.putBoolean("authentication", true);
                        editor.putInt("user_id", userList.get(i).getUser_id());
                        editor.commit();

                    }
                }
                //if authentication is set to false
                Boolean authentication = preference.getBoolean("authentication", false);
                //if authentication is correct
                if (authentication) {
                    //it will redirect to MainActivity class
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    //start activity
                    startActivity(intent);
                } else {
                    //Send the error if the username and password does not match
                    name.setError("Username or password doesn't match!");
                    //toast message also display
                    Toast.makeText(LoginActivity.this, "User not found!", Toast.LENGTH_LONG).show();
                }


            }
        });
        //signup button is clicked then it will redirect to signup page
        //Create an anonymous implementation of OnClickListener for signup
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creating the intent object for page redirection
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                //activity starts
                startActivity(intent);
            }
        });


    }
}
