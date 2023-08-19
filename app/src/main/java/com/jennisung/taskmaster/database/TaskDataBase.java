package com.jennisung.taskmaster.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.jennisung.taskmaster.daos.TaskDao;
import com.jennisung.taskmaster.models.Task;


@TypeConverters({TaskDataBaseClassConverter.class})
@Database(entities = {Task.class}, version = 1) //Don't update version, it will break!
public abstract class TaskDataBase extends RoomDatabase {
    public abstract TaskDao taskDao();
}
