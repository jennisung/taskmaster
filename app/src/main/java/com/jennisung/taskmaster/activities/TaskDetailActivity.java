package com.jennisung.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.jennisung.taskmaster.MainActivity;
import com.jennisung.taskmaster.R;

public class TaskDetailActivity extends AppCompatActivity {
    private static final String TAG = "TaskDetailActivity";

    ImageView taskImageView;
    Task currentTask;

    Intent callingIntent;

    String s3ImageKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        callingIntent = getIntent();

        Intent gettingIntent = getIntent();
        String taskDetailString = null;

        taskImageView = findViewById(R.id.TaskDetailActivityTaskImageView);

        setupTaskImageView();

        if (gettingIntent != null) {
            taskDetailString = gettingIntent.getStringExtra(MainActivity.USER_INPUT_EXTRA_TAG);

            TextView taskDetailText = findViewById(R.id.TaskDetailPageHeading);

            if (taskDetailString != null) {
                taskDetailText.setText(taskDetailString);
            }
        }

        TextView taskDescriptionText = findViewById(R.id.TaskDetailPageTexts);
        taskDescriptionText.setText(R.string.TaskDetailPageText);
    }

    void setupTaskImageView() {
        String taskId = "";
        if(callingIntent != null) {
            taskId = callingIntent.getStringExtra(MainActivity.TASK_ID_EXTRA_TAG);
        }

        if(!taskId.equals("")) {
            Amplify.API.query(
                    ModelQuery.get(Task.class, taskId),
                    success -> {
                        Log.i(TAG, "successfully found task with id: " + success.getData().getId());
                        currentTask = success.getData();
                    },
                    failure -> {
                        Log.i(TAG,"Failed to query task from DB: " + failure.getMessage());
                    }
            );
        }
    }
}


//public class TaskDetailActivity extends AppCompatActivity {
//    private static String TAG = "TaskDetailActivity";
//
//    ImageView taskImageView;
//
//    Intent callingIntent;
//
//    String s3ImageKey;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_task_detail);
//
//        callingIntent = getIntent();
//
//        Intent gettingIntent = getIntent();
//        String taskDetailString = null;
//
//        setupTaskImageView();
//
//        taskImageView = findViewById(R.id.TaskDetailActivityTaskImageView);
//
//
//        if (gettingIntent != null) {
//            taskDetailString = gettingIntent.getStringExtra(MainActivity.USER_INPUT_EXTRA_TAG);
//
//            TextView taskDetailText = findViewById(R.id.TaskDetailPageHeading);
//
//            if (taskDetailString != null) {
//                taskDetailText.setText(taskDetailString);
//            }
//
//        }
//
//        TextView taskDescriptionText = findViewById(R.id.TaskDetailPageTexts);
//        taskDescriptionText.setText(R.string.TaskDetailPageText);
//
//
//        void setupTaskImageView(){
//            String taskId = "";
//            if(calling != null) {
//                taskId = callingIntent.getStringExtra(MainActivity.TASK_ID_EXTRA_TAG);
//            }
//
//            if(!taskId.equals("")) {
//                Amplify.API.query(
//                        ModelQuery.get(Task.class, taskId),
//                        success -> {
//                            Log.i(TAG, "successfully found task with id: " + success.getData().getId());
//                            currentTask = success.getData();
//                            populateImageView();
//                        },
//                        failure -> {
//                            Log.i(TAG,"Failed to query task from DB: " + failure.getMessage());
//                        }
//                );
//            }
//
//        }
//    }
//}





