package com.jennisung.taskmaster.models;

//TODO 2-1: create a data class

import java.util.Date;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Task {

    @PrimaryKey(autoGenerate = true)
    public long id;
    String title;
    String body;

    java.util.Date dateCreated;
    TaskStatusEnum status;

    public Task(String title, String body, java.util.Date dateCreated, TaskStatusEnum status) {
        this.title = title;
        this.body = body;
        this.dateCreated = dateCreated;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public TaskStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TaskStatusEnum status) {
        this.status = status;
    }
}