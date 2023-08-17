package com.jennisung.taskmaster.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jennisung.taskmaster.MainActivity;
import com.jennisung.taskmaster.R;
import com.jennisung.taskmaster.activities.TaskDetailActivity;
import com.jennisung.taskmaster.models.Task;

import java.util.List;

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
        String taskFragmentText = (position+1) + ". " + tasks.get(position).getTitle();
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

    //TODO step 1-8 make a viewholder class to hold our fragment(nested within product list recycler handler)
    public static class TaskListViewHolder extends RecyclerView.ViewHolder {

        public TaskListViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

