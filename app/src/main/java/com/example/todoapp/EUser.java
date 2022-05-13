package com.example.todoapp;
//import required Library

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * EUser class will describe the Entity (which represents the SQLite table) for your words.
 * Each property in the class represents a column in the tableRoom will ultimately use these
 * properties to both create the table and instantiate objects from rows in the database
 */
//@Entity Annotations identify how each part of this class relates to an entry in the database i.e.user_table
@Entity(tableName = "user_table")
//public class ETodo
public class EUser {
    //@PrimaryKey define the uniqueness for user_id
    @PrimaryKey
    private int user_id;
    //@NonNull state it will not return null values and @ColumnInfo map the specific column form database
    @NonNull
    //map the name column from the db table and private string name
    @ColumnInfo(name = "name")
    private String name;
    //password column could not be empty of string primitive data type
    @NonNull
    @ColumnInfo(name = "password")
    private String password;

    /**
     * get the data
     *
     * @return user_id
     */
    public int getUser_id() {
        return user_id;
    }

    /**
     * set the user id
     *
     * @param user_id
     */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    /**
     * set the name of the user
     *
     * @param name
     */
    public void setName(@NonNull String name) {
        this.name = name;
    }

    //set password with string data type
    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    //@ignore annotation can be ignored
    @Ignore
    public EUser() {
    }

    /**
     * set the user data
     *
     * @param user_id
     * @param name
     * @param password
     */
    public EUser(int user_id, @NonNull String name, String password) {
        this.user_id = user_id;
        this.name = name;
        this.password = password;
    }

    @Ignore
    public EUser(@NonNull String name, String password) {
        this.name = name;
        this.password = password;
    }

    /**
     * get user name
     *
     * @return name cannot be null
     */
    @NonNull
    public String getName() {
        return name;
    }

    /**
     * get password
     *
     * @return password cannot be null
     */
    @NonNull
    public String getPassword() {
        return password;
    }


}

