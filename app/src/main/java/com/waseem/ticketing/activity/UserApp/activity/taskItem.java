package com.waseem.ticketing.activity.UserApp.activity;

/**
 * Created by waseem on 3/23/2017.
 */

public class taskItem {

    public String task;
    public String taskName;



    public void setTaskname(String taskName){this.taskName = taskName;}

    public String getTaskname() {
        return taskName;
    }



    public void setTask(String task) {
        this.task = task;
    }


    public String getTask() {
        return task;
    }


    public taskItem(String task , String taskName) {
        this.task = task;
        this.taskName = taskName;

    }
}
