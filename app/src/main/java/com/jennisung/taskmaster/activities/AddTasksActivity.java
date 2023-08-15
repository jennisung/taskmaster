package com.jennisung.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


import com.jennisung.taskmaster.R;

public class AddTasksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tasks);

        Button addTaskButton = (Button) findViewById(R.id.AddTasksAddTaskButton);

//        addTaskButton.setOnClickListener(v -> {
//        });


    }


}