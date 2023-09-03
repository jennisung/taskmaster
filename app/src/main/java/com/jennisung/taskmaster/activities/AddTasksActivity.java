package com.jennisung.taskmaster.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.appcompat.app.AppCompatActivity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AddTasksActivity extends AppCompatActivity {
    CompletableFuture<List<Team>> teamsFuture = null;
    ActivityResultLauncher<Intent> activityResultLauncher;
    private String s3ImageKey = "";

    private final String TAG = "AddTaskActivity";
    private Button saveButton;
    private EditText taskTitleEditText;
    private EditText taskDescriptionEditText;
    private Spinner taskStatusSpinner;
    private Spinner taskTeamSpinner;
    ImageView taskImageView;



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

        taskImageView = findViewById(R.id.AddTaskTaskImageView);
        activityResultLauncher = getImagePickingActivityResultLauncher();

        setupTaskTeamSpinner();
        setupTaskImageView();
    }

    void setupTaskImageView(){
        taskImageView.setOnClickListener(view -> {
            launchImageSelectionIntent();
        });
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
                    .taskImageS3Key(s3ImageKey)
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


    void launchImageSelectionIntent() {
        Intent imageFilePickingIntent = new Intent(Intent.ACTION_GET_CONTENT);
        imageFilePickingIntent.setType("*/*");
        imageFilePickingIntent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/jpeg", "image/png"});
        activityResultLauncher.launch(imageFilePickingIntent);
    }

    ActivityResultLauncher<Intent> getImagePickingActivityResultLauncher() {
        ActivityResultLauncher<Intent> imagePickingActivityResultLauncher =
                registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        new ActivityResultCallback<ActivityResult>() {
                            @Override
                            public void onActivityResult(ActivityResult result) {
                                Uri pickedImageFileUri = result.getData().getData();
                                try {
                                    InputStream pickedImageInputStream = getContentResolver().openInputStream(pickedImageFileUri);
                                    String pickedImageFilename = getFileNameFromUri(pickedImageFileUri);
                                    Log.i(TAG, "Succeeded in getting input stream from file on phone! Filename is: " + pickedImageFilename);
                                    uploadInputStreamToS3(pickedImageInputStream, pickedImageFilename, pickedImageFileUri);
                                } catch (FileNotFoundException fnfe) {
                                    Log.e(TAG, "Could not get file form file picker! " + fnfe.getMessage(), fnfe);
                                }
                            }
                        }
                );

        return imagePickingActivityResultLauncher;
    }


//    void uploadInputStreamToS3(InputStream pickedImageInputStream, String pickedImageFilename, Uri pickedImageFileUri) {
//        Amplify.Storage.uploadInputStream(
//                pickedImageFilename,
//                pickedImageInputStream,
//                success -> {
//                    Log.i(TAG, "Succeeded in getting file uploaded to S3! Key is: " + success.getKey());
//                    s3ImageKey = success.getKey();
//                    InputStream pickedImageInputStreamCopy = null;
//                    try {
//                        pickedImageInputStreamCopy = getContentResolver().openInputStream(pickedImageFileUri);
//                    } catch (FileNotFoundException fnfe) {
//                        Log.e(TAG, "Could not get file stream from URI! " + fnfe.getMessage(), fnfe);
//                    }
//                    taskImageView.setImageBitmap(BitmapFactory.decodeStream(pickedImageInputStreamCopy));
//                },
//                failure -> {
//                    Log.e(TAG, "Failure in uploading file to S3 with filename: " + pickedImageFilename + " with error: " + failure.getMessage());
//                }
//        );
//    }

    void uploadInputStreamToS3(InputStream pickedImageInputStream, String pickedImageFilename, Uri pickedImageFileUri) {
        Amplify.Storage.uploadInputStream(
                pickedImageFilename,
                pickedImageInputStream,
                success -> {
                    Log.i(TAG, "Succeeded in getting file uploaded to S3! Key is: " + success.getKey());
                    s3ImageKey = success.getKey();
                    InputStream pickedImageInputStreamCopy = null;
                    try {
                        pickedImageInputStreamCopy = getContentResolver().openInputStream(pickedImageFileUri);
                    } catch (FileNotFoundException fnfe) {
                        Log.e(TAG, "Could not get file stream from URI! " + fnfe.getMessage(), fnfe);
                    }
                    taskImageView.setImageBitmap(BitmapFactory.decodeStream(pickedImageInputStreamCopy));
                },
                failure -> {
                    Log.e(TAG, "Failure in uploading file to S3 with filename: " + pickedImageFilename, failure);
                    if (failure.getCause() != null) {
                        Log.e(TAG, "Root cause: ", failure.getCause());
                    }
                }
        );
    }


    // Taken from https://stackoverflow.com/a/25005243/16889809

    @SuppressLint("Range")
    public String getFileNameFromUri(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
}



