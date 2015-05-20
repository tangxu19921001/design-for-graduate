package com.example.util;

import com.example.dao.TaskManageDAO;
import com.example.entity.TaskDetail;
import com.example.entity.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 2015/5/6.
 */
public class ToJsonImpl implements ToJson{
    TaskManageDAO taskManageDAO = new TaskManageDAO();
    @Override
    public String toJson(List<TaskDetail> taskDetailList) {
        JSONArray jsonArray = new JSONArray();
        for (int j =0;j<taskDetailList.size();j++){
            TaskDetail taskDetail = taskDetailList.get(j);
            JSONObject jsonObject = new JSONObject();

            try{
                jsonObject.put("taskId",taskDetail.getTaskId());
                jsonObject.put("taskTime",taskDetail.getTaskTime());
                jsonObject.put("taskName",taskDetail.getTaskName());
                jsonObject.put("taskContent",taskDetail.getTaskContent());
                if (taskDetail.getTaskMoney()==null){
                    taskDetail.setTaskMoney(0);
                }
                jsonObject.put("taskMoney",taskDetail.getTaskMoney());
            }catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(jsonObject);
        }
       return jsonArray.toString();
    }

    @Override
    public String toAchievementJson(User user) {

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try{
            int money;
            int achievementPoint;
            int userId;
            if (user.getMoney() ==null){
                money = 0;
            }else{
                money = user.getMoney();
            }
            if (user.getAchievementPoint() ==null){
                achievementPoint = 0;
            }else{
                achievementPoint = user.getAchievementPoint();
            }
            userId = user.getUserId();
            ArrayList<TaskDetail>taskDetails = taskManageDAO.findTaskDetailByUserId(user.getUserId());
            if (taskDetails != null) {
                for (int j = 0; j < taskDetails.size(); j++) {
                    TaskDetail taskDetail = taskDetails.get(j);
                    JSONObject taskJsonObject = new JSONObject();

                    try {

                        taskJsonObject.put("taskId", taskDetail.getTaskId());
                        taskJsonObject.put("taskTime", taskDetail.getTaskTime());
                        taskJsonObject.put("taskName", taskDetail.getTaskName());
                        taskJsonObject.put("taskContent", taskDetail.getTaskContent());
                        taskJsonObject.put("isReceived",taskDetail.getIsReceived());
                        //TODO 任务金钱
                        if (taskDetail.getTaskMoney()==null){
                            taskDetail.setTaskMoney(0);
                        }
                        taskJsonObject.put("taskMoney",taskDetail.getTaskMoney());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    jsonArray.put(taskJsonObject);
                }
                jsonObject.put("json_array",jsonArray);
            }
            jsonObject.put("money",money);
            jsonObject.put("userId",userId);
            jsonObject.put("achievement_point",achievementPoint);
            jsonObject.put("isSuccess",1);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }


}
