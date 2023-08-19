package com.jennisung.taskmaster.models;

import androidx.annotation.NonNull;

public enum TaskStatusEnum {
    NEW("New"),
    COMPLETE("Complete"),
    ASSIGNED("Assigned"),
    IN_PROGRESS("In Progress");

    private String text;
    TaskStatusEnum(String text) {
        this.text = text;
    }
    public String getText(){
        return this.text;
    }

    public static TaskStatusEnum fromString(String text) {
        for (TaskStatusEnum tse : TaskStatusEnum.values()) {
            if (tse.text.equalsIgnoreCase(text)) {
                return tse;
            }
        }
        return null;
    }
    @NonNull
    @Override
    public String toString() {
        return this.text;
    }
}
