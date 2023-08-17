package com.jennisung.taskmaster.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jennisung.taskmaster.R;
//import com.jennisung.taskmaster.fragments.TaskListFragment;

public class TaskRecyclerViewAdapter extends RecyclerView.Adapter {

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //TODO 1-7: inflate the fragment
        View taskFragment = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task_list, parent, false);

        //TODO step 1-9: attach fragment to the viewholder
        return new TaskListViewHolder(taskFragment);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    //TODO step 1-8 make a viewholder class to hold our fragment(nested within product list recycler handler)
    public static class TaskListViewHolder extends RecyclerView.ViewHolder {

        public TaskListViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}