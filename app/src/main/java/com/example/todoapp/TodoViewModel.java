package com.example.todoapp;
//import required Library

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * TodoViewModel holds your app's UI data in a lifecycle-conscious way that survives configuration changes
 * Created a class called TodoViewModel that gets the Application as a parameter and extends AndroidViewModel
 */
public class TodoViewModel extends AndroidViewModel {
    //Added a private member variable to hold a reference to the repository
    private TodoRepository repository;
    private LiveData<List<ETodo>> allTodos;

    public TodoViewModel(@NonNull Application application) {
        //super call the parent constructor
        super(application);
        //Implemented a constructor that creates the TodoRepository
        repository = new TodoRepository(application);
        //In the constructor, initialized the allTodos LiveData using the repository
        allTodos = repository.getAllTodoList();
    }

    //Added a getAllTodos() method to return a cached list of data
    public LiveData<List<ETodo>> getAllTodos() {
        return allTodos;
    }

    //Created a wrapper insert() and Update() method that calls the Repository's insert() and update() method.
    // In this way, the implementation of insert() and update() is encapsulated from the UI
    public void insert(ETodo todo) {
        repository.insert(todo);
    }

    public void update(ETodo todo) {
        repository.update(todo);
    }

    public void updateIsCompleted(int id, boolean is_completed) {
        repository.updateIsCompleted(id, is_completed);
    }

    //created a wrapper deleteById() method that calls the repository's deletedById method
    public void deleteById(ETodo eTodo) {
        repository.deleteById(eTodo);
    }

    /**
     * created a wrapper deleteAll() method that call the repository with id in it parameter
     *
     * @param id
     */
    public void deleteAll(int id) {
        repository.deleteAll(id);
    }

    //Added a getAll() method to return a cached list of data
    public List<ETodo> getAll() {
        return repository.getAll();
    }

    //created a wrapper deleteAll() method that return the repository with id in it parameter
    public ETodo getTodoById(int id) {
        return repository.getTodoById(id);
    }

    //created a wrapper deleteAllCompleted() method that call the repository with id and is_completed parameter
    public void deleteAllCompleted(int id, boolean is_completed) {
        repository.deleteAllCompleted(id, is_completed);
    }

}
