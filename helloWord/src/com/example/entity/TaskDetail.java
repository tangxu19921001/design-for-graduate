package com.example.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by john on 2015/4/27.
 */
public class TaskDetail implements Serializable {
    //taskId是主键。在添加任务时候使用这张表
    private Integer taskId;
    private String taskName;
    private Date taskTime;
    private String taskContent;



    //构造函数
    private Integer taskMoney;
    //是否被接受了
    //是否被完成了
    private Boolean isFinished;
    private Boolean isReceived;


    public Boolean getIsReceived() {
        return isReceived;
    }

    public void setIsReceived(Boolean isReceived) {
        this.isReceived = isReceived;
    }

    public Boolean getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(Boolean isFinished) {
        this.isFinished = isFinished;
    }
    public TaskDetail(){}
    public TaskDetail(String taskName,Date taskTime, String taskContent){

        this.taskName = taskName;
        this.taskTime = taskTime;
        this.taskContent = taskContent;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /* private String promulgatorName;*/
    private User user;
    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Date getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(Date taskTime) {
        this.taskTime = taskTime;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public Integer getTaskMoney() {
        return taskMoney;
    }

    public void setTaskMoney(Integer taskMoney) {
        this.taskMoney = taskMoney;
    }
/*
    public String getPromulgatorName() {
        return promulgatorName;
    }

    public void setPromulgatorName(String promulgatorName) {
        this.promulgatorName = promulgatorName;
    }*/



}
