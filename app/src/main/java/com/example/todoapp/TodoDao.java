package com.example.todoapp;
//import required Library

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Dao is the data access object
 * creating thr interface TodoDao because the query is execute separately
 *
 * @Dao annotation identifies it as a DAO class for Room
 */
@Dao
public interface TodoDao {
    //The @Insert annotation is a special DAO method annotation where you don't have to provide any SQL
    @Insert
    void insert(ETodo todo);

    /**
     * query that deleted the data from todo_table with selected id
     *
     * @param id
     */
    @Query("DELETE FROM todo_table WHERE user_id = :id")
    void deleteAll(int id);

    //requires that you provide a SQL query as a string parameter to the annotation.
    @Query("DELETE FROM todo_table WHERE user_id=:id AND is_completed = :is_completed")
    void deleteAllCompleted(int id, boolean is_completed);

    //delete by id from ETodo object
    @Delete
    void deleteById(ETodo todo);

    //query that select all data from todo table where id is equal to passed id
    //get data related to id
    @Query("SELECT * FROM todo_table WHERE id=:id")
    ETodo getTodoById(int id);

    /**
     * allowing the update of the same word multiple times by passing a conflict resolution strategy
     *
     * @param todo
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(ETodo... todo);

    /**
     * query that update the todo_table column with selected id and is_completed
     *
     * @param id
     * @param is_completed
     */
    @Query("UPDATE todo_table SET is_completed = :is_completed WHERE id = :id")
    void updateIsComplete(int id, boolean is_completed);

    /**
     * Observable queries are read operations that emit new values whenever there are changes
     * to any of the tables that are referenced by the query.
     *
     * @return list of todos in desc
     */
    @Query("SELECT * FROM todo_table ORDER BY todo_date, priority desc")
    LiveData<List<ETodo>> getAllTodos();

    @Query("SELECT * FROM todo_table ORDER BY todo_date, priority desc")
    List<ETodo> getAll();


}

