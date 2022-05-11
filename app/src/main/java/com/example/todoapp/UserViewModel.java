package com.example.todoapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    UserRepository repository;
    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
    }

    public void insert(EUser user) {
        repository.insert(user);
    }

    public void update(EUser user) {repository.update(user);}

    public void deleteById(EUser user) {repository.deleteById(user);}

    public EUser getUserById(int id) {
        return repository.getUserById(id);
    }

    public List<EUser> getAllUsers() {
        return repository.getAllUsers();
    }

}
