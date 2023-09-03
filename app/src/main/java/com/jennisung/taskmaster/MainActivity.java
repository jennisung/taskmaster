package com.jennisung.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;
import com.jennisung.taskmaster.activities.AddTasksActivity;
import com.jennisung.taskmaster.activities.AllTasksActivity;
import com.jennisung.taskmaster.activities.SettingsActivity;
import com.jennisung.taskmaster.adapter.TaskRecyclerViewAdapter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    //    public static final String DATABASE_NAME = "jennisung_taskmaster_database";
    public static final String USER_INPUT_EXTRA_TAG = "taskName";
    public static final String TASK_NAME_EXTRA_TAG = "taskName";

    public static final String TASK_ID_EXTRA_TAG = "taskID";

    List<Task> tasks = new ArrayList<>();
    SharedPreferences preferences;
    TaskRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);


//        manualS3FileUpload();
        // createTeamInstances();
        // setupTaskButtons();
        setupAddTaskPageButton();
        setupAllTasksPageButton();
        setupSettingsPageButton();
        updateTaskListFromDatabase();
        setupRecyclerView(tasks);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setupUsernameTasksTitle();
        updateTaskListFromDatabase();


    }

    void createTeamInstances() {
        Team team1 = Team.builder()
                .name("Team One")
                .build();
        Amplify.API.mutate(
                ModelMutation.create(team1),
                successResponse -> Log.i(TAG, "MainActivity.createTeamInstances(): made a contact successfully"),
                failureResponse -> Log.i(TAG, "MainActivity.createContactInstances(): contact failed with this response: " + failureResponse)
        );

        Team team2 = Team.builder()
                .name("Team Two")
                .build();
        Amplify.API.mutate(
                ModelMutation.create(team2),
                successResponse -> Log.i(TAG, "MainActivity.createTeamInstances(): made a contact successfully"),
                failureResponse -> Log.i(TAG, "MainActivity.createContactInstances(): contact failed with this response: " + failureResponse)
        );

        Team team3 = Team.builder()
                .name("Team Three")
                .build();
        Amplify.API.mutate(
                ModelMutation.create(team3),
                successResponse -> Log.i(TAG, "MainActivity.createTeamInstances(): made a contact successfully"),
                failureResponse -> Log.i(TAG, "MainActivity.createContactInstances(): contact failed with this response: " + failureResponse)
        );
    }


    void updateTaskListFromDatabase() {
        Amplify.API.query(
                ModelQuery.list(Task.class),
                success -> {
                    Log.i(TAG, "Read tasks successfully!");
                    tasks.clear();

                    String selectedTeamName = preferences.getString(SettingsActivity.TEAM_TAG, null);
                    Log.i(TAG, "Selected Team Name: " + selectedTeamName);

                    for (Task databaseTask : success.getData()) {
                        Team taskTeam = databaseTask.getTeam();
                        if (selectedTeamName == null || (taskTeam != null && taskTeam.getName().equals(selectedTeamName))) {
                            tasks.add(databaseTask);
                        }
                    }

                    runOnUiThread(() -> {
                        adapter.notifyDataSetChanged();
                    });
                },
                failure -> Log.i(TAG, "Did not read tasks successfully.")
        );
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


    void setupRecyclerView(List<Task> tasks) {
        RecyclerView taskRecyclerView = (RecyclerView) findViewById(R.id.MainActivityTaskRecyclerView);

        RecyclerView.LayoutManager taskLayoutManager = new LinearLayoutManager(this);
        taskRecyclerView.setLayoutManager(taskLayoutManager);
        adapter = new TaskRecyclerViewAdapter(tasks, this);

        taskRecyclerView.setAdapter(adapter);
    }


    private void setupUsernameTasksTitle() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String username = preferences.getString("username", "");

        Log.d("MainActivity", "Username retrieved: " + username);

        if (!username.isEmpty()) {
            String myTasksTitleTextView = username + "'s Tasks";
            ((TextView) findViewById(R.id.usernameTasksTextView)).setText(myTasksTitleTextView);
        }
    }

    void manualS3FileUpload() {
        // create a test file to be saved to S3
        String testFilename = "testFilename";
        File testFile = new File(getApplicationContext().getFilesDir(), testFilename);
        // write to test file with BufferedWriter
        try {
            BufferedWriter testFileBufferedWriter = new BufferedWriter(new FileWriter(testFile));
            testFileBufferedWriter.append("some test text here\nAnother line of test text");
            testFileBufferedWriter.close(); // Makes sure you do this or your text may not be saved
        } catch(IOException ioe) {
            Log.e(TAG, "Could not write file locally with filename: " + testFilename);
        }

        // create an S3 key
        String testFileS3Key = "someFileOnS3.txt";

        // call Storage.uploadFile
        Amplify.Storage.uploadFile(
                testFileS3Key,
                testFile,
                success -> {
                    Log.i(TAG, "S3 uploaded successfully! Key is: " + success.getKey());
                },
                failure -> {
                    Log.i(TAG, "S3 upload failed! " + failure.getMessage());
                }
        );
    }


}

