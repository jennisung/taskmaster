package com.jennisung.taskmaster;

import static com.jennisung.taskmaster.activities.SettingsActivity.TEAM_TAG;

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
import com.jennisung.taskmaster.activities.TaskDetailActivity;
import com.jennisung.taskmaster.adapter.TaskRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    //    public static final String DATABASE_NAME = "jennisung_taskmaster_database";
    public static final String USER_INPUT_EXTRA_TAG = "taskName";
    public static final String TASK_NAME_EXTRA_TAG = "taskName";
    List<Task> tasks = new ArrayList<>();
    SharedPreferences preferences;
    TaskRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);


//        createTeamInstances();
//        setupTaskButtons();

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
        RecyclerView taskRecyclerView = (RecyclerView) findViewById(R.id.MainActivityTaskRecyclerView);

        RecyclerView.LayoutManager taskLayoutManager = new LinearLayoutManager(this);
        taskRecyclerView.setLayoutManager(taskLayoutManager);
        adapter = new TaskRecyclerViewAdapter(tasks, this);

        taskRecyclerView.setAdapter(adapter);
    }

    private void setupUsernameTasksTitle() {
        String username = preferences.getString(SettingsActivity.USERNAME_TAG, "");

        String teamName = preferences.getString(TEAM_TAG, "All");

        Log.d("MainActivity", "Username retrieved: " + username);

        if (!username.isEmpty()) {
            String myTasksTitleTextView = username + "'s Tasks";
            ((TextView) findViewById(R.id.usernameTasksTextView)).setText(myTasksTitleTextView);
        }
    }

}



//    void setupRecyclerView(List<Task> tasks){
//        // TODO: Step 1-2 Grab the recyclerview
//        RecyclerView taskRecyclerView = (RecyclerView) findViewById(R.id.MainActivityTaskRecyclerView);
//
//
//        // TODO: Step 1-3 set the layoutmanager for the recycler view to the linear layout
//        RecyclerView.LayoutManager taskLayoutManager = new LinearLayoutManager(this);
//        taskRecyclerView.setLayoutManager(taskLayoutManager);
//
//        // TODO: step 1-5 create and attack recyclerview.adapter to recycler view
//        // TaskRecyclerViewAdapter adapter = new TaskRecyclerViewAdapter();
//        //TODO: step 2-3 hand data items from main activity to our recyclerview adapter
//        //TaskRecyclerViewAdapter adapter = new TaskRecyclerViewAdapter(taskList, this);
//
//        //TODO step 3-2 hand in activity context to the adapter
//        adapter = new TaskRecyclerViewAdapter(tasks, this);
//
//        taskRecyclerView.setAdapter(adapter);
//    }



//    void updateTaskListFromDatabase() {
//        Amplify.API.query(
//                ModelQuery.list(Task.class),
//                success -> {
//                    Log.i(TAG, "Read tasks successfully!");
//                    tasks.clear();
//
//                    String selectedTeamName = preferences.getString(TEAM_TAG, null);
//
//                    for (Task databaseTask : success.getData()) {
//                        if (selectedTeamName == null || databaseTask.getTeam().getName().equals(selectedTeamName)) {
//                            tasks.add(databaseTask);
//                        }
//
//                    }
//
//                    runOnUiThread(() -> {
//                        adapter.notifyDataSetChanged();
//                    });
//                },
//                failure -> Log.i(TAG, "Did not read tasks successfully.")
//        );
//    }