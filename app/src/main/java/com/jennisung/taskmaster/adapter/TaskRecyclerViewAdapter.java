package com.jennisung.taskmaster.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Task;
import com.jennisung.taskmaster.MainActivity;
import com.jennisung.taskmaster.R;
import com.jennisung.taskmaster.activities.TaskDetailActivity;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.TaskListViewHolder> {

    // TODO step 2-3: create product list vairable and constructor within adapter
    public List<Task> tasks;
    public Context context;

    //TODO : step 3-2cont: create a context variable and update constructor
    Context callingActivity;
    public TaskRecyclerViewAdapter(List<Task> tasks, Context callingActivity) {
        this.tasks = tasks;
        this.callingActivity = callingActivity;
    }

    @NonNull
    @Override
    public TaskListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //TODO 1-7: inflate the fragment
        View taskFragment = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task_list, parent, false);

        //TODO step 1-9: attach fragment to the viewholder
        return new TaskListViewHolder(taskFragment);
    }


    @Override
    public void onBindViewHolder(@NonNull TaskListViewHolder holder, int position) {
        // TODO: Step 2-4: Bind data items to Fragments inside of ViewHolders
        TextView taskFragmentTextView = (TextView) holder.itemView.findViewById(R.id.taskFragmentTextView);
        String dateString = formatDateString(tasks.get(position));
        String taskFragmentText = (position + 1) + ". " + tasks.get(position).getTitle()
                + "\n" + dateString
                + "\n" + tasks.get(position).getStatus();
        taskFragmentTextView.setText(taskFragmentText);

        // TODO: Step 3-3: Create an onClickListener, make an intent inside of it, and call this intent with an extra to go to a new activity

        View taskViewHolder = holder.itemView;
        taskViewHolder.setOnClickListener(v -> {
            Intent goToTaskInfoIntent = new Intent(callingActivity, TaskDetailActivity.class);

            goToTaskInfoIntent.putExtra(MainActivity.TASK_NAME_EXTRA_TAG, tasks.get(position).getTitle());

            callingActivity.startActivity(goToTaskInfoIntent);
        });
    }




    @Override
    public int getItemCount() {
        //TODO step 2-5 make size of the list dynamic based on size of product list
        return tasks.size();
    }


    private String formatDateString(Task task) {
        DateFormat dateCreatedIso8601InputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        dateCreatedIso8601InputFormat.setTimeZone(TimeZone.getTimeZone(("America/New_York")));
        DateFormat dateCreatedOutputFormat = new SimpleDateFormat("dd MMM yyyy HH:mm");
        dateCreatedOutputFormat.setTimeZone(TimeZone.getDefault());
        String dateCreateString = "";

        try {
            {
                Date dateCreateJavaDate = dateCreatedIso8601InputFormat.parse(task.getDateCreated().format());
                if(dateCreateJavaDate != null) {
                    dateCreateString = dateCreatedOutputFormat.format(dateCreateJavaDate);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateCreateString;
    }

    //TODO step 1-8 make a viewholder class to hold our fragment(nested within product list recycler handler)
    public static class TaskListViewHolder extends RecyclerView.ViewHolder {

        public TaskListViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

