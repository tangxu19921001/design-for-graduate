package com.example.util;

import com.example.entity.TaskDetail;
import com.example.entity.User;

import java.util.List;

/**
 * Created by john on 2015/5/6.
 */
public interface ToJson {
    String toJson(List<TaskDetail> taskDetailList);
    String toAchievementJson(User user);
}
