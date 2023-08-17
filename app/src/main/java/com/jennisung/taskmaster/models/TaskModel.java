package com.jennisung.taskmaster.models;

//TODO 2-1: create a data class
//A Task should have a title, a body, and a state.


public class TaskModel {
    String title;
    String body;
    TaskStatus status;

    //The state should be one of “new”, “assigned”, “in progress”, or “complete”.
    public enum TaskStatus {
        NEW,
        ASSIGNED,
        IN_PROGRESS,
        COMPLETE,
    }

    public TaskModel(String title, String body, TaskStatus status) {
        this.title = title;
        this.body = body;
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

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}