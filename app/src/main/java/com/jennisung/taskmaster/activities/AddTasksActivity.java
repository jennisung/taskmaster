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
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.TaskStatusEnum;
import com.google.android.material.tabs.TabLayout;
import com.jennisung.taskmaster.R;

import java.util.Date;

public class AddTasksActivity extends AppCompatActivity {
    private final String TAG = "AddTaskActivity";
    private Button saveButton;
    private EditText taskTitleEditText;
    private EditText taskDescriptionEditText;
    private Spinner taskStatusSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tasks);

        saveButton = findViewById(R.id.AddTasksAddTaskButton);
        taskStatusSpinner = findViewById(R.id.AddTaskStatusSpinner);

        setupTaskStatusSpinner(taskStatusSpinner);
        setupSaveButton(taskStatusSpinner);

        taskTitleEditText = findViewById(R.id.AddTasksInputFieldForTaskTitle);
        taskDescriptionEditText = findViewById(R.id.AddTasksTaskDescriptionInputForm);
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
            Task taskToSave = Task.builder()
                    .title(taskTitleEditText.getText().toString())
                    .body(taskDescriptionEditText.getText().toString())
                    .dateCreated(new Temporal.DateTime(new Date(), 0))
                    .status((TaskStatusEnum) taskStatusSpinner.getSelectedItem())
                    .build();

            Amplify.API.mutate(
                    ModelMutation.create(taskToSave),
                    successResponse -> Log.i(TAG, "AddTasksActivity.setupSaveButton() : made task successfully"),
                    failureResponse -> Log.i(TAG, "AddTasksActivity.setupSaveButton() : failed with this response " + failureResponse)
            );

            Toast.makeText(AddTasksActivity.this, "Task has been Saved!!", Toast.LENGTH_SHORT).show();
        });
    }
}

//package com.jennisung.taskmaster.activities;


////import static com.jennisung.taskmaster.MainActivity.DATABASE_NAME;
//import androidx.appcompat.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import com.amplifyframework.api.graphql.model.ModelMutation;
//import com.amplifyframework.core.Amplify;
//import com.amplifyframework.core.model.temporal.Temporal;
//import com.amplifyframework.datastore.generated.model.Task;
//import com.amplifyframework.datastore.generated.model.TaskStatusEnum;
//import com.google.android.material.tabs.TabLayout;
//import com.jennisung.taskmaster.R;
//
//import java.util.Date;
//
//public class AddTasksActivity extends AppCompatActivity {
//    private final String TAG = "AddTaskActivity";
//    Button saveButton;
//    EditText taskTitleEditText;
//    EditText taskDescriptionEditText;
//
//    Spinner taskStatusSpinner;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_tasks);
//
//        Button saveButton = (Button) findViewById(R.id.AddTasksAddTaskButton);
//
//        Spinner taskStatusSpinner = (Spinner) findViewById(R.id.AddTaskStatusSpinner);
//
//        setupTaskStatusSpinner(taskStatusSpinner);
//        setupSaveButton(taskStatusSpinner);
//
//        taskTitleEditText = findViewById(R.id.AddTasksInputFieldForTaskTitle);
//        taskDescriptionEditText = findViewById(R.id.AddTasksTaskDescriptionInputForm);
//
//    }
//
//    void setupTaskStatusSpinner(Spinner taskStatusSpinner) {
//        taskStatusSpinner.setAdapter(new ArrayAdapter<>(
//                this,
//                android.R.layout.simple_spinner_item,
//                TaskStatusEnum.values()
//        ));
//    }
//
//    void setupSaveButton(Spinner taskStatusSpinner) {
//        saveButton.setOnClickListener(v -> {
//        Task taskToSave = Task.builder()
//                .title(taskTitleEditText.getText().toString())
//                .body(taskDescriptionEditText.getText().toString())
//                .dateCreated(new Temporal.DateTime(new Date(), 0))
//                .status((TaskStatusEnum) taskStatusSpinner.getSelectedItem())
//                .build();
//
//            Amplify.API.mutate(
//                    ModelMutation.create(taskToSave),
//                    successResponse -> Log.i(TAG, "AddTasksActivity.setupSaveButton() : made task successfully"),
//                    failureResponse -> Log.i(TAG, "AddTasksActivity.setupSaveButton() : failed with this response " + failureResponse)
//            );
//
//            Toast.makeText(AddTasksActivity.this, "Task has been Saved!!", Toast.LENGTH_SHORT).show();
//        });
//    }
//}
//
//
