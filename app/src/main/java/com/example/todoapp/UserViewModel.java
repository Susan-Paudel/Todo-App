package com.example.todoapp;
//import required Library

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

//create class UserViewModel that extends AndroidViewModel
public class UserViewModel extends AndroidViewModel {
    //Declare a variable for UserRepository class
    UserRepository repository;

    /**
     * function to fetch all the users from the repository
     * UserViewModel is created
     *
     * @param application
     */
    public UserViewModel(@NonNull Application application) {
        //called parent constructor
        super(application);
        //crating the instance of repository
        repository = new UserRepository(application);
    }

    // function to insert a user from repository
    public void insert(EUser user) {
        repository.insert(user);
    }

    // function to update a user from repository
    public void update(EUser user) {
        repository.update(user);
    }

    // function to delete particular a user from repository
    public void deleteById(EUser user) {
        repository.deleteById(user);
    }

    // function to get a user by its it from repository
    public EUser getUserById(int id) {
        return repository.getUserById(id);
    }

    //  // function to fetch all the users from repository
    public List<EUser> getAllUsers() {
        return repository.getAllUsers();
    }

}
