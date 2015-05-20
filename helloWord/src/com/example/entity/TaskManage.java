package com.example.entity;

import java.io.Serializable;

/**
 * Created by john on 2015/4/25.
 */
public class TaskManage implements Serializable
{


    private int taskManageId;
    private int userId ;
    private int taskId ;
   /* //发布者名字
    private String promulgator;*/
    //接受者名字
   
    //用户
    private User user;
    //评价
    private String evaluate;
    private Integer receiverId;

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }


    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public int getTaskManageId() {
        return taskManageId;
    }

    public void setTaskManageId(int taskManageId) {
        this.taskManageId = taskManageId;
    }




    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

   /* public String getPromulgator() {
        return promulgator;
    }

    public void setPromulgator(String promulgator) {
        this.promulgator = promulgator;
    }*/

    



}
