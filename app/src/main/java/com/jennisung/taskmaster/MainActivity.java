package com.jennisung.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.jennisung.taskmaster.activities.AddTasksActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addTasksButton = (Button) findViewById(R.id.MainActivityAddTaskButton);

        addTasksButton.setOnClickListener(v -> {
            Intent addTaskFormIntent = new Intent(MainActivity.this, AddTasksActivity.class);

//            startActivities(addTaskFormIntent);
        });

        Button allTaskButton = (Button) findViewById(R.id.MainActivityAllTasksButton);


    }
}