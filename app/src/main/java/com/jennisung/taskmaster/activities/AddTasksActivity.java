package com.jennisung.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.TaskStatusEnum;
import com.amplifyframework.datastore.generated.model.Team;
import com.jennisung.taskmaster.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AddTasksActivity extends AppCompatActivity {

    CompletableFuture<List<Team>> teamsFuture = null;


    private final String TAG = "AddTaskActivity";
    private Button saveButton;
    private EditText taskTitleEditText;
    private EditText taskDescriptionEditText;
    private Spinner taskStatusSpinner;

    private Spinner taskTeamSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tasks);

        teamsFuture = new CompletableFuture<>();

        saveButton = findViewById(R.id.AddTasksAddTaskButton);
        taskStatusSpinner = findViewById(R.id.AddTaskStatusSpinner);
        taskTeamSpinner = findViewById(R.id.addTaskTeamSpinner);

        setupTaskStatusSpinner(taskStatusSpinner);
        setupSaveButton(taskStatusSpinner);

        taskTitleEditText = findViewById(R.id.AddTasksInputFieldForTaskTitle);
        taskDescriptionEditText = findViewById(R.id.AddTasksTaskDescriptionInputForm);

        setupTaskTeamSpinner();

    }

    private void setupTaskStatusSpinner(Spinner taskStatusSpinner) {
        taskStatusSpinner.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                TaskStatusEnum.values()
        ));
    }


    private void setupSaveButton(Spinner taskStatusSpinner) {
        saveButton.setOnClickListener(v -> {
            String selectedTeamString = taskTeamSpinner.getSelectedItem().toString();
            List<Team> teams = null;

            try {
                teams = teamsFuture.get();
            } catch (InterruptedException ie) {
                Log.e(TAG, "InterruptedException while getting teams.");
                Thread.currentThread().interrupt();
            } catch (ExecutionException ee){
                Log.e(TAG, "ExecutionException while getting teams");
            }


            assert teams != null;
            Team selectedTeam = teams.stream().filter(t -> t.getName().equals(selectedTeamString)).findAny().orElseThrow(RuntimeException::new);

            Task taskToSave = Task.builder()
                    .title(taskTitleEditText.getText().toString())
                    .body(taskDescriptionEditText.getText().toString())
                    .dateCreated(new Temporal.DateTime(new Date(), 0))
                    .status((TaskStatusEnum) taskStatusSpinner.getSelectedItem())
                    .team(selectedTeam)
                    .build();

            Amplify.API.mutate(
                    ModelMutation.create(taskToSave),
                    successResponse -> Log.i(TAG, "AddTasksActivity.setupSaveButton() : made task successfully"),
                    failureResponse -> Log.i(TAG, "AddTasksActivity.setupSaveButton() : failed with this response " + failureResponse)
            );

            Toast.makeText(AddTasksActivity.this, "Task has been Saved!!", Toast.LENGTH_SHORT).show();
        });
    }


    private void setupTaskTeamSpinner() {
        Amplify.API.query(
                ModelQuery.list(Team.class),
                success -> {
                    Log.i(TAG, "Read teams Successfully");
                    ArrayList<String> teamNames = new ArrayList<>();
                    ArrayList<Team> teams = new ArrayList<>();
                    for(Team team : success.getData()){
                        teams.add(team);
                        teamNames.add(team.getName());
                    }

                    teamsFuture.complete(teams);

                    runOnUiThread(() -> {
                        ArrayAdapter<String> teamAdapter = new ArrayAdapter<>(
                                this,
                                android.R.layout.simple_spinner_item,
                                teamNames
                        );
                        teamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        taskTeamSpinner.setAdapter(teamAdapter);
                    });
                },

                failure -> {
                    teamsFuture.complete(null);
                    Log.i(TAG, "Did not read teams successfully!!");
                }
        );
    }

}




//    private void setupTaskTeamSpinner() {
//        Amplify.API.query(
//                ModelQuery.list(Team.class),
//                success -> {
//                    Log.i(TAG, "Read teams Successfully");
//                    ArrayList<String> teamNames = new ArrayList<>();
//                    ArrayList<Team> teams = new ArrayList<>();
//                    for(Team team : success.getData()){
//                        teams.add(team);
//
////                        teamNames.add(teams.getFullName());
//
//                    }
//
//                    teamsFuture.complete(teams);
//
////                    runOnUiThread(() -> {
////                                taskTeamSpinner.setAdapter(new ArrayAdapter<>(
////                                        this,
////                                        android.R.layout.simple_spinner_item,
////                                        teamNames
////                                ));
////                    });
//                },
//
//                failure -> {
//                    teamsFuture.complete(null);
//                    Log.i(TAG, "Did not read teams successfully!!");
//                }
//        );
//    }
