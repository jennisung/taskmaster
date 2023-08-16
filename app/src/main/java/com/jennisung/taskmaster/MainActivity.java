package com.jennisung.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

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

public class MainActivity extends AppCompatActivity {
    public static final String USER_INPUT_EXTRA_TAG = "taskName";
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        setupTaskButtons();
        setupAddTaskPageButton();
        setupAllTasksPageButton();
        setupSettingsPageButton();
    }

    void setupTaskButtons() {
        setupTaskButton(R.id.buttonTask1);
        setupTaskButton(R.id.buttonTask2);
        setupTaskButton(R.id.buttonTask3);
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

    @Override
    protected void onResume() {
        super.onResume();

        setupUsernameTasksTitle();
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


//package com.jennisung.taskmaster;
//

//import androidx.appcompat.app.AppCompatActivity;
//import androidx.preference.PreferenceManager;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.TextView;
//
//import com.jennisung.taskmaster.activities.AddTasksActivity;
//import com.jennisung.taskmaster.activities.AllTasksActivity;
//import com.jennisung.taskmaster.activities.SettingsActivity;
//import com.jennisung.taskmaster.activities.TaskDetailActivity;
//
//public class MainActivity extends AppCompatActivity {
//    public static final String USER_INPUT_EXTRA_TAG = "taskName";
//    SharedPreferences preferences;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        preferences = PreferenceManager.getDefaultSharedPreferences(this);
//
//        setupTaskButtons();
//
//        //go to add task page button
//        Button goToAddTaskPageButton = findViewById(R.id.MainActivityAddTaskButton);
//        goToAddTaskPageButton.setOnClickListener(v -> {
//            Intent addTasksFormIntent = new Intent(MainActivity.this, AddTasksActivity.class);
//            startActivity(addTasksFormIntent);
//        });
//
//        // go to all task page button
//        Button goToAllTasksPageButton = findViewById(R.id.MainActivityAllTasksButton);
//        goToAllTasksPageButton.setOnClickListener(v -> {
//            Intent allTasksFormIntent = new Intent(MainActivity.this, AllTasksActivity.class);
//            startActivity(allTasksFormIntent);
//        });
//
//        // go to settings page button
//        ImageButton settingsButton = findViewById(R.id.MainActivitySettingsButton);
//        settingsButton.setOnClickListener(v -> {
//            Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
//            startActivity(settingsIntent);
//        });
//
//    }
//
//
//
//    public void setupTaskButtons() {
//        Button taskOneButton = findViewById(R.id.buttonTask1);
//        setTaskButtonClickListener(taskOneButton);
//        Button taskTwoButton = findViewById(R.id.buttonTask2);
//        setTaskButtonClickListener(taskTwoButton);
//        Button taskThreeButton = findViewById(R.id.buttonTask3);
//        setTaskButtonClickListener(taskThreeButton);
//    }
//
//    private void setTaskButtonClickListener(Button button) {
//        button.setOnClickListener(v -> {
//            Intent intent = new Intent(MainActivity.this, TaskDetailActivity.class);
//            intent.putExtra(MainActivity.USER_INPUT_EXTRA_TAG, button.getText().toString());
//            startActivity(intent);
//        });
//
//    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        String username = preferences.getString(SettingsActivity.USERNAME_TAG, "");
//
//        Log.d("MainActivity", "Username retrieved: " + username);
//
//        if (!username.isEmpty()) {
//            String myTasksTitleTextView = username + "'s Tasks";
//            ((TextView) findViewById(R.id.usernameTasksTextView)).setText(myTasksTitleTextView);
//        }
//    }
//
//
//}
