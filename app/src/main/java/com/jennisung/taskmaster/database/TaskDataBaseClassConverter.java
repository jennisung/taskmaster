package com.jennisung.taskmaster.database;

import androidx.room.TypeConverter;

import java.util.Date;


//For the java.util.Date dateCreated; time stamp
public class TaskDataBaseClassConverter {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

}
