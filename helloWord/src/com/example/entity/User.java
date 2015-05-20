package com.example.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2015/3/13.
 */
public class User implements Serializable {

    private Integer userId;
    private String name;
    private String password;
    //User对应的Money
    private Integer money;
    private Integer achievementPoint;

    //一个用户表对应多个任务管理表，谁发布的，谁接受的，任务Id.
    private Set taskManage = new HashSet(0);
    //一个用户表对应多个任务表,任务时间，名字，内容
    private Set taskDetail = new HashSet(0);

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(String name, String password, String info, Set taskManage, Set relationships) {
        this.name = name;
        this.password = password;
        this.taskManage = taskManage;

    }

    public Integer getAchievementPoint() {
        return achievementPoint;
    }

    public void setAchievementPoint(Integer achievementPoint) {
        this.achievementPoint = achievementPoint;
    }

    public Set getTaskDetail() {
        return taskDetail;
    }

    public void setTaskDetail(Set taskDetail) {
        this.taskDetail = taskDetail;
    }


    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }


    public Set getTaskManage() {
        return taskManage;
    }

    public void setTaskManage(Set taskManage) {
        this.taskManage = taskManage;
    }


    public User() {

    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer id) {
        this.userId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
