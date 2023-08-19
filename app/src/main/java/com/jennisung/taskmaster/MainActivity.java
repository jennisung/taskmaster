package com.jennisung.taskmaster;

import com.jennisung.taskmaster.database.TaskDataBase;
import com.jennisung.taskmaster.models.Task;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jennisung.taskmaster.activities.AddTasksActivity;
import com.jennisung.taskmaster.activities.AllTasksActivity;
import com.jennisung.taskmaster.activities.SettingsActivity;
import com.jennisung.taskmaster.activities.TaskDetailActivity;
import com.jennisung.taskmaster.adapter.TaskRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    public static final String DATABASE_NAME = "jennisung_taskmaster_database";
    public static final String USER_INPUT_EXTRA_TAG = "taskName";
    public static final String TASK_NAME_EXTRA_TAG = "taskName";
    List<Task> tasks = new ArrayList<>();
    SharedPreferences preferences;
    TaskDataBase taskDataBase;
    TaskRecyclerViewAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        setupDataBase();
//        setupTaskButtons();
        setupAddTaskPageButton();
        setupAllTasksPageButton();
        setupSettingsPageButton();
        setupRecyclerView(tasks);

    }

    @Override
    protected void onResume() {
        super.onResume();

        setupUsernameTasksTitle();
        updateTaskListFromDatabase();
    }

    void updateTaskListFromDatabase() {
        tasks.clear();
        tasks.addAll(taskDataBase.taskDao().findAll());
        adapter.notifyDataSetChanged();
    }
    void setupDataBase(){
        taskDataBase = Room.databaseBuilder(
                        getApplicationContext(),
                        TaskDataBase.class,
                        DATABASE_NAME)
                        //.fallbackToDestructiveMigration() // If Room gets confused, it tosses your database; turn this off in production!
                        .allowMainThreadQueries()
                        .build();

        tasks = taskDataBase.taskDao().findAll();
    }

    void setupTaskButton(int buttonId) {
        Button taskButton = findViewById(buttonId);
        taskButton.setOnClickListener(v -> {
            Intent taskDetailIntent = new Intent(MainActivity.this, TaskDetailActivity.class);
            taskDetailIntent.putExtra(USER_INPUT_EXTRA_TAG, taskButton.getText().toString());
            startActivity(taskDetailIntent);
        });
    }

    void setupAddTaskPageButton() {
        Button addTaskButton = findViewById(R.id.MainActivityAddTaskButton);
        addTaskButton.setOnClickListener(v -> {
            Intent addTaskIntent = new Intent(MainActivity.this, AddTasksActivity.class);
            startActivity(addTaskIntent);
        });
    }

    void setupAllTasksPageButton() {
        Button allTasksButton = findViewById(R.id.MainActivityAllTasksButton);
        allTasksButton.setOnClickListener(v -> {
            Intent allTasksIntent = new Intent(MainActivity.this, AllTasksActivity.class);
            startActivity(allTasksIntent);
        });
    }


    void setupSettingsPageButton() {
        ImageButton settingsButton = findViewById(R.id.MainActivitySettingsButton);
        settingsButton.setOnClickListener(v -> {
            Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);
        });
    }


        void setupRecyclerView(List<Task> tasks){
        // TODO: Step 1-2 Grab the recyclerview
        RecyclerView taskRecyclerView = (RecyclerView) findViewById(R.id.MainActivityTaskRecyclerView);


        // TODO: Step 1-3 set the layoutmanager for the recycler view to the linear layout
        RecyclerView.LayoutManager taskLayoutManager = new LinearLayoutManager(this);
        taskRecyclerView.setLayoutManager(taskLayoutManager);

        // TODO: step 1-5 create and attack recyclerview.adapter to recycler view
        // TaskRecyclerViewAdapter adapter = new TaskRecyclerViewAdapter();
        //TODO: step 2-3 hand data items from main activity to our recyclerview adapter
        //TaskRecyclerViewAdapter adapter = new TaskRecyclerViewAdapter(taskList, this);

        //TODO step 3-2 hand in activity context to the adapter
         adapter = new TaskRecyclerViewAdapter(tasks, this);

        taskRecyclerView.setAdapter(adapter);
    }

    private void setupUsernameTasksTitle() {
        String username = preferences.getString(SettingsActivity.USERNAME_TAG, "");

        Log.d("MainActivity", "Username retrieved: " + username);

        if (!username.isEmpty()) {
            String myTasksTitleTextView = username + "'s Tasks";
            ((TextView) findViewById(R.id.usernameTasksTextView)).setText(myTasksTitleTextView);
        }
    }


}

