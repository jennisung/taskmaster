package com.jennisung.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.jennisung.taskmaster.activities.AddTasksActivity;
import com.jennisung.taskmaster.activities.AllTasksActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // add task button
        Button goToAddTaskPageButton = findViewById(R.id.MainActivityAddTaskButton);
        goToAddTaskPageButton.setOnClickListener(v -> {
            Intent addTasksFormIntent = new Intent(MainActivity.this, AddTasksActivity.class);
            startActivity(addTasksFormIntent);
        });


        // all task button
        Button goToAllTasksPageButton = findViewById(R.id.MainActivityAllTasksButton);
        goToAllTasksPageButton.setOnClickListener(v -> {
            Intent allTasksFormIntent = new Intent(MainActivity.this, AllTasksActivity.class);
            startActivity(allTasksFormIntent);
        });
    }
}