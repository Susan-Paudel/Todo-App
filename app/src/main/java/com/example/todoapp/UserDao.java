package com.example.todoapp;
//import required Library

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Dao is the data access object
 * creating thr interface UserDao because the query is execute separately
 *
 * @Dao annotation identifies it as a DAO class for Room
 */
@Dao
public interface UserDao {
    /**
     * allowing the insert of the same word multiple times by passing a conflict resolution strategy
     * insert the data in EUser class
     *
     * @param todo
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EUser todo);


    //query that select all data from user table where id is equal to passed id
    //get data related to provide id
    @Query("SELECT * FROM user_table WHERE user_id=:id")
    EUser getUserById(int id);

    //delete by id from ETodo object
    @Delete
    void deleteById(EUser eUser);

    //fetch all users
    @Query("SELECT * FROM user_table")
    List<EUser> getAllUsers();

    /**
     * allowing the update of the same word multiple times by passing a conflict resolution strategy
     * update the user password
     *
     * @param user
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(EUser... user);


}

