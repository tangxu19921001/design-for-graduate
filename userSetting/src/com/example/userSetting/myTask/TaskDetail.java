package com.example.userSetting.myTask;

import java.util.Date;

/**
 * 任务实体
 * Created by john on 2015/5/13.
 */
public class TaskDetail {

    private int taskId;
    private String taskTime;
    private String taskContent;
    private String taskName;
    private int taskMoney;

    public int getTaskMoney() {
        return taskMoney;
    }

    public void setTaskMoney(int taskMoney) {
        this.taskMoney = taskMoney;
    }



    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }


}
