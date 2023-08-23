package com.jennisung.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.jennisung.taskmaster.MainActivity;
import com.jennisung.taskmaster.R;

public class TaskDetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        Intent gettingIntent = getIntent();
        String taskDetailString = null;

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
}





