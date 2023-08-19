package com.jennisung.taskmaster.daos;
import com.jennisung.taskmaster.models.Task;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;



@Dao
public interface TaskDao {
    @Insert
    public void insertTask(Task task);

    @Query("SELECT * FROM Task")
    public List<Task> findAll();

    @Query("SELECT * FROM Task ORDER BY title ASC")
    List<Task> findAllSortedByName();

    @Query("SELECT * FROM Task WHERE id = :id")
    Task findByAnId(long id);
}

