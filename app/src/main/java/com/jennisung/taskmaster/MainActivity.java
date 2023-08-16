package com.jennisung.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import com.jennisung.taskmaster.activities.AddTasksActivity;
import com.jennisung.taskmaster.activities.AllTasksActivity;
import com.jennisung.taskmaster.activities.SettingsActivity;
import com.jennisung.taskmaster.activities.TaskDetailActivity;

public class MainActivity extends AppCompatActivity {
    public static final String USER_INPUT_EXTRA_TAG = "taskName";
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupTaskButtons();

        //go to add task page button
        Button goToAddTaskPageButton = findViewById(R.id.MainActivityAddTaskButton);
        goToAddTaskPageButton.setOnClickListener(v -> {
            Intent addTasksFormIntent = new Intent(MainActivity.this, AddTasksActivity.class);
            startActivity(addTasksFormIntent);
        });

        // go to all task page button
        Button goToAllTasksPageButton = findViewById(R.id.MainActivityAllTasksButton);
        goToAllTasksPageButton.setOnClickListener(v -> {
            Intent allTasksFormIntent = new Intent(MainActivity.this, AllTasksActivity.class);
            startActivity(allTasksFormIntent);
        });

        // go to settings page button
        ImageButton settingsButton = findViewById(R.id.MainActivitySettingsButton);
        settingsButton.setOnClickListener(v -> {
            Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);
        });


    }

    public void setupTaskButtons() {
        Button taskOneButton = findViewById(R.id.buttonTask1);
        setTaskButtonClickListener(taskOneButton);
        Button taskTwoButton = findViewById(R.id.buttonTask2);
        setTaskButtonClickListener(taskTwoButton);
        Button taskThreeButton = findViewById(R.id.buttonTask3);
        setTaskButtonClickListener(taskThreeButton);
    }

    private void setTaskButtonClickListener(Button button) {
        button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TaskDetailActivity.class);
            intent.putExtra(MainActivity.USER_INPUT_EXTRA_TAG, button.getText().toString());
            startActivity(intent);
        });



    }
}
