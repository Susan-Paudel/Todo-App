package com.example.todoapp;
//import required Library

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.todoapp.ETodo;
import com.example.todoapp.TodoRoomDatabase;

import java.util.List;

/**
 * Repository manages queries and allows you to use multiple backends and
 * fetch data from a network or use results cached in a local database
 * Creating class TodoRepository
 */
public class TodoRepository {
    //Declare required TodoDoa and LiveData object
    private TodoDao mTodoDAO;
    private LiveData<List<ETodo>> allTodoList;

    /**
     * function to fetch all the users from the room database
     * Save data in a local database using Room
     *
     * @param application
     */
    public TodoRepository(Application application) {
        //creating the object of TodoRoomDatabase
        TodoRoomDatabase database = TodoRoomDatabase.getDatabase(application);
        mTodoDAO = database.mTodoDao();
        //initializing the mTodoDao into allTodoList
        allTodoList = mTodoDAO.getAllTodos();
    }

    /**
     * TodoDao is class and getmTodoDAO is method called a method signature
     *
     * @return mTodoDAO
     */
    public TodoDao getmTodoDAO() {
        return mTodoDAO;
    }

    /**
     * set the TodoDAO
     *
     * @param mTodoDAO
     */
    public void setmTodoDAO(TodoDao mTodoDAO) {
        this.mTodoDAO = mTodoDAO;
    }
    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    // getAllWords method returns the LiveData list of words from Room

    /**
     * @return allTodoList of words from TodoRoomDatabase
     */
    public LiveData<List<ETodo>> getAllTodoList() {
        return allTodoList;
    }

    //set the data to allTodoList
    public void setAllTodoList(LiveData<List<ETodo>> allTodoList) {
        this.allTodoList = allTodoList;
    }

    /**
     * function to insert a todo lists
     * Creates a new asynchronous task,This constructor must be invoked on the UI thread
     *
     * @param eTodo a reference of Class in your method signature
     */
    public void insert(ETodo eTodo) {
        //object of asynchronous of mTodoDAO
        new insertTodoAysncTask(mTodoDAO).execute(eTodo);
    }

    // function to update a todo lists
    public void update(ETodo eTodo) {
        new updateTodoAysncTask(mTodoDAO).execute(eTodo);
    }

    /**
     * updateIsCompleted function set the value in mTodoDAO when update is success
     *
     * @param id
     * @param is_completed
     */
    public void updateIsCompleted(int id, boolean is_completed) {
        mTodoDAO.updateIsComplete(id, is_completed);
    }

    // function to delete a particular list items
    public void deleteById(ETodo eTodo) {
        new deleteByIdTodoAysnc(mTodoDAO).execute(eTodo);
    }

    // function to delete all the list items
    public void deleteAll(int id) {
        mTodoDAO.deleteAll(id);
    }

    //function to fetch all the list from the list
    public List<ETodo> getAll() {
        return mTodoDAO.getAll();
    }

    //function help too delete only the completed checked items
    public void deleteAllCompleted(int id, boolean is_completed) {
        mTodoDAO.deleteAllCompleted(id, is_completed);
    }

    /**
     * function that get the list data by its id
     *
     * @param id
     * @return mTodoDAO.getTodoById(id)
     */
    public ETodo getTodoById(int id) {
        return mTodoDAO.getTodoById(id);
    }

    /*
     * an asynchronous task is defined by a computation that runs on a background thread and
     * whose result is published on the UI thread. An asynchronous task is defined by 3 generic types,
     * called Params, Progress and Result, and 4 steps, called onPreExecute, doInBackground, onProgressUpdate
     * and onPostExecute.
     */
    // function to insert a todo lists into the room db
    private static class insertTodoAysncTask extends AsyncTask<ETodo, Void, Void> {
        private TodoDao mTodoDao;

        private insertTodoAysncTask(TodoDao todoDAO) {
            mTodoDao = todoDAO;
        }

        /**
         * This step is used to perform background computation (update) that can take a long time
         *
         * @param eTodos
         * @return null
         */
        @Override
        protected Void doInBackground(ETodo... eTodos) {
            mTodoDao.insert(eTodos[0]);
            return null;
        }
    }

    // function to update a todo lists into the room db
    private static class updateTodoAysncTask extends AsyncTask<ETodo, Void, Void> {
        private TodoDao mTodoDao;

        private updateTodoAysncTask(TodoDao todoDAO) {
            mTodoDao = todoDAO;
        }

        /**
         * This step is used to perform background computation (update) that can take a long time
         *
         * @param eTodos
         * @return null
         */
        @Override
        protected Void doInBackground(ETodo... eTodos) {
            mTodoDao.update(eTodos[0]);
            return null;
        }
    }

    // function to delete a todo lists into the room db
    //AsyncTask holds the input,process,output in it signature method
    private static class deleteByIdTodoAysnc extends AsyncTask<ETodo, Void, Void> {
        private TodoDao mTodoDao;

        private deleteByIdTodoAysnc(TodoDao todoDAO) {
            mTodoDao = todoDAO;
        }

        /**
         * This step is used to perform background computation (update) that can take a long time
         *
         * @param eTodos
         * @return null
         */
        @Override
        protected Void doInBackground(ETodo... eTodos) {
            mTodoDao.deleteById(eTodos[0]);
            return null;
        }
    }


}

