package com.example.todoapp;
//import required Library

import androidx.room.TypeConverter;

import java.util.Date;

/**
 * date converter class to convert my date object
 * converting the long primitive into Date
 * to persist instances of Date in TodoRoomDatabase. Room doesn't know how to persist Date objects,DateConverter is defined
 */
public class DateConverter {
    /**
     * @param timeStamp
     * @return if timestamp is null then it will pass the new date
     * @TypeConverters annotation is define so that Room knows about the Dateconverter class that is defined
     */
    @TypeConverter
    public static Date toDate(Long timeStamp) {
        return timeStamp == null ? null : new Date(timeStamp);
    }

    /**
     * @param date
     * @return when date is null then it will pass today date
     */
    @TypeConverter
    public static Long toTimeStamp(Date date) {
        return date == null ? null : date.getTime();
    }
}

