package com.example.todoapp;
//import required Library

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Defines the database configuration and serves as the app's main access point to the persisted data.
 *
 * @Database annotation parameters to declare the entities that belong in the database and set the version number
 * TodoRoomdatabase class for Room must be abstract and extend RoomDatabase
 */
@Database(entities = {ETodo.class, EUser.class}, version = 1, exportSchema = false)
public abstract class TodoRoomDatabase extends RoomDatabase {
    /**
     * The database class must define an abstract method that has zero arguments
     * and returns an instance of the DAO class, for each DAO class.
     *
     * @return TodoDao
     */
    public abstract TodoDao mTodoDao();

    public abstract UserDao mUserDao();

    //creating static TodoRoomDatabase instance
    public static TodoRoomDatabase INSTANCE;

    /**
     * getDatabase returns the singleton. It'll create the database the first time it's accessed,
     * using Room's database builder to create a RoomDatabase object in the application context from
     * the TodoRoomDatabase class and names it "todo.db"
     *
     * @param context
     * @return Instance
     */

    public static TodoRoomDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (TodoRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TodoRoomDatabase.class,
                            "todo.db")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    //this method save the state in RoomDatabase
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(INSTANCE).execute();
        }

    };

    //prepopulate a Room database from a prepackaged database file i.e. todoDao and userDao
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private TodoDao todoDao;
        private UserDao userDao;

        private PopulateDbAsyncTask(TodoRoomDatabase db) {
            todoDao = db.mTodoDao();
            userDao = db.mUserDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            /**
             * Inserting data in todo_table
             * creating the object of date class
             */
            Date todoDate = new Date();
            //try catch
            try {
                //format the date in year, month ,day
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                todoDate = format.parse("2022/05/08");
            }//if try block does not execute
            catch (ParseException ex) {
                ex.printStackTrace();
            }
            //insert given data into db with user_id zero
            todoDao.insert(new ETodo("Get some Water!", "water costs around 3$ per litre. So, buy one litres!",
                    todoDate, 1, false, 0));
            todoDao.insert(new ETodo("Go jogging alone!", "Jogging is a good cardio and is also beneficial for your mental health!",
                    todoDate, 2, false, 0));

            /**
             * Inserting data in user_table
             */
            userDao.insert(new EUser(0, "Susan", "123"));
            return null;
        }
    }

}

