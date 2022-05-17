package com.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Task implements Serializable {
    @SerializedName("isAddTask")
    private boolean isAddTask;  //  True - значит добавляем задачу, False - удаляем задачу
    @SerializedName("task")
    private String task;
    @SerializedName("numberTask")
    private Integer numberTask;

    @Override
    public String toString() {
        return super.toString();
    }

    public Task(String task, boolean isAddTask){
        this.task = task;
        this.isAddTask = isAddTask;
    }

    public Task(Integer numberTask, boolean isAddTask){
        this.numberTask = numberTask;
        this.isAddTask = isAddTask;
    }
    public Task(String task, Integer numberTask, boolean isAddTask){
        this.task = task;
        this.numberTask = numberTask;
        this.isAddTask = isAddTask;
    }

    public Task() {
    }

    public boolean getIsAddTask(){
        return isAddTask;
    }

    public Integer getNumberTask(){
        return numberTask;
    }

    public String getTask(){
        return task;
    }
}
