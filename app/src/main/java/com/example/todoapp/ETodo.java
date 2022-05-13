package com.example.todoapp;
//import required Library

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

/**
 * ETodo class will describe the Entity (which represents the SQLite table) for your words.
 * Each property in the class represents a column in the tableRoom will ultimately use these
 * properties to both create the table and instantiate objects from rows in the database
 */
//@Entity Annotations identify how each part of this class relates to an entry in the database i.e.todo_table
@Entity(tableName = "todo_table")
//public class ETodo
public class ETodo {
    //@PrimaryKey define the uniqueness
    @PrimaryKey(autoGenerate = true)
    //declare integer id variable
    private int id;
    //@NonNull state it will not return null values and @ColumnInfo map the specific column form database
    @NonNull
    @ColumnInfo(name = "title")
    //private title, it is access with in the class
    private String title;
    //map the description column from the db table and private string description
    @ColumnInfo(name = "description")
    private String description;
    //@TypeConverter to modify data values during the reading and writing of a mapped todoDate attribute from DataConverter class
    @TypeConverters({DateConverter.class})
    @ColumnInfo(name = "todo_date")
    private Date todoDate;
    //priority db column
    @ColumnInfo(name = "priority")
    private int priority;
    //is_completed db column
    @ColumnInfo(name = "is_completed")
    private boolean isCompleted;
    //user_id db column
    @ColumnInfo(name = "user_id")
    private int user_id;

    /**
     * get the user id
     *
     * @return user_id
     */
    public int getUser_id() {
        return user_id;
    }

    /**
     * set the user id
     */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    //constructor od the class and @Ignore state to ignore particular tests
    @Ignore
    public ETodo() {
    }

    /**
     * parameterized method which cannot be null
     *
     * @param title
     * @param description
     * @param todoDate
     * @param priority
     * @param isCompleted
     * @param user_id
     */
    public ETodo(@NonNull String title, String description, Date todoDate, int priority, boolean isCompleted, int user_id) {
        this.title = title;
        this.description = description;
        this.todoDate = todoDate;
        this.priority = priority;
        this.isCompleted = isCompleted;
        this.user_id = user_id;
    }

    //get the id
    public int getId() {
        return id;
    }

    //set the id
    public void setId(int id) {
        this.id = id;
    }

    //get title
    @NonNull
    public String getTitle() {
        return title;
    }

    //set the Title ans cannot be null
    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    /**
     * get data
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    //set description
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * get Date
     *
     * @return todoDate
     */
    public Date getTodoDate() {
        return todoDate;
    }

    //set Date
    public void setTodoDate(Date todoDate) {
        this.todoDate = todoDate;
    }

    /**
     * get int priority
     *
     * @return priority
     */
    public int getPriority() {
        return priority;
    }

    //set the value of priority
    public void setPriority(int priority) {
        this.priority = priority;
    }

    //boolean iscompleted
    public boolean isCompleted() {
        return isCompleted;
    }

    /**
     * set the boolean value
     *
     * @param completed
     */
    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}

