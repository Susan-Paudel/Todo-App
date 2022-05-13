package com.example.todoapp.data;
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
     * Save data in a local database using Room
     * @param application
     */
    public TodoRepository(Application application){
        TodoRoomDatabase database = TodoRoomDatabase.getDatabase(application);
        mTodoDAO = database.mTodoDao();
        //initializing the mTodoDao into allTodoList
        allTodoList = mTodoDAO.getAllTodos();
    }

    /**
     * TodoDao is class and getmTodoDAO is method called a method signature
     * @return mTodoDAO
     */
    public TodoDao getmTodoDAO() {
        return mTodoDAO;
    }

    /**
     * set the TodoDAO
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
     *
     * Creates a new asynchronous task,This constructor must be invoked on the UI thread
     * @param eTodo a reference of Class in your method signature
     */
    public void insert(ETodo eTodo){
        //object of asynchronous of mTodoDAO
        new insertTodoAysncTask(mTodoDAO).execute(eTodo);
    }

    public void update(ETodo eTodo) {
        new updateTodoAysncTask(mTodoDAO).execute(eTodo);
    }

    /**
     * updateIsCompleted function set the value in mTodoDAO when update is success
     * @param id
     * @param is_completed
     */
    public void updateIsCompleted(int id, boolean is_completed) {mTodoDAO.updateIsComplete(id, is_completed);}
    //
    public void deleteById(ETodo eTodo){
        new deleteByIdTodoAysnc(mTodoDAO).execute(eTodo);
    }

    public void deleteAll(int id) { mTodoDAO.deleteAll(id);}

    public List<ETodo> getAll() { return  mTodoDAO.getAll();}

    public void deleteAllCompleted(int id, boolean is_completed) {
        mTodoDAO.deleteAllCompleted(id, is_completed);
    }

    public ETodo getTodoById(int id){
        return mTodoDAO.getTodoById(id);
    }

    private static class insertTodoAysncTask extends AsyncTask<ETodo, Void, Void> {
        private TodoDao mTodoDao;
        private insertTodoAysncTask(TodoDao todoDAO){
            mTodoDao=todoDAO;
        }

        @Override
        protected Void doInBackground(ETodo... eTodos) {
            mTodoDao.insert(eTodos[0]);
            return null;
        }
    }

    private static class updateTodoAysncTask extends AsyncTask<ETodo, Void, Void> {
        private TodoDao mTodoDao;
        private updateTodoAysncTask(TodoDao todoDAO){
            mTodoDao=todoDAO;
        }

        @Override
        protected Void doInBackground(ETodo... eTodos) {
            mTodoDao.update(eTodos[0]);
            return null;
        }
    }
    private static class deleteByIdTodoAysnc extends AsyncTask<ETodo, Void, Void>{
        private TodoDao mTodoDao;
        private deleteByIdTodoAysnc(TodoDao todoDAO){
            mTodoDao=todoDAO;
        }

        @Override
        protected Void doInBackground(ETodo... eTodos) {
            mTodoDao.deleteById(eTodos[0]);
            return null;
        }
    }


}

