package com.example.servlet;





import com.example.dao.TaskDetailDAO;
import com.example.dao.TaskManageDAO;
import com.example.dao.UserDAO;
import com.example.entity.TaskDetail;
import com.example.entity.TaskManage;
import com.example.entity.User;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by john on 2015/5/7.
 */
public class VerifyTaskFinishedServlet extends HttpServlet {
    private TaskDetailDAO taskDetailDAO = new TaskDetailDAO();
    private  UserDAO userDAO = new UserDAO();
    private  TaskManageDAO taskManageDAO = new TaskManageDAO();
    private String evaluate;
    private  int taskId;
    private  int userId;
    private int receiverId;
    private int taskManageId;
    private  int position;
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)throws ServletException,
            IOException {
        doPost(request,response);

    }
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)throws ServletException,
            IOException {



        StringBuffer sb = new StringBuffer("");
        String result = "";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    request.getInputStream(), "utf-8"));
            String temp;
            while ((temp = br.readLine()) != null) {
                sb.append(temp);
            }
            br.close();
            result = sb.toString();
            //打印android端上传的JSON数据
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println(result);

        try {
            JSONObject jsonObject = new JSONObject(result);
            evaluate = jsonObject.getString("evaluate");
            userId = jsonObject.getInt("userId");
            position  = jsonObject.getInt("position");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList<TaskManage> taskManages = taskManageDAO.getManageById(userId);

         TaskManage taskManage = taskManages.get(position);
        taskId = taskManage.getTaskId();
     //   receiverId = taskManage.getReceiverId();


        boolean isFinishEvaluate = taskManageDAO.addEvaluate(evaluate,taskManage);


        //根据 taskId 更改表的完成为 完成 ，userId的金钱减掉 taskId悬赏的金钱， receiver 的金钱增加 taskId悬赏的金钱，成就点+1
        TaskDetail taskDetail = taskDetailDAO.getTaskDetailById(taskId);
        taskDetailDAO.finishTask(taskDetail,true);
        Integer taskMoney = taskDetail.getTaskMoney();
        //TODO 判断钱是否为负数
        //通过两个用户分别取出发布和接受者的信息
        User promulgator = userDAO.getUserById(userId);
        Integer userMoney = promulgator.getMoney();
        Integer userAchievement = promulgator.getAchievementPoint();

//TODO
     //   User receiver = userDAO.getUserById(receiverId);
       //TODO
    //    Integer receiverMoney = receiver.getMoney();
      //  Integer receiverAchievement = receiver.getAchievementPoint();
        //更新金钱
        if(userMoney ==null){
            userMoney = 0;
        }
     /*   if(receiverMoney ==null){
            receiverMoney = 0;
        }*/

        if (taskMoney == null){
            taskMoney = 10;
        }
        if (userAchievement ==null){
            userAchievement =0;
        }
//        if (receiverAchievement ==null){
//            receiverAchievement = 0;
//        }


        int promulgatorLeftMoney = userMoney - taskMoney;
     //   int receiverLeftMoney =receiverMoney + taskMoney;
        userAchievement = userAchievement +1;
      //  receiverAchievement = receiverAchievement +1;



        //更新user的money
        userDAO.updateUserMoney(promulgator , promulgatorLeftMoney);
        //TODO
     //   userDAO.updateUserMoney(receiver,receiverLeftMoney);
        userDAO.updateUserAchievement(promulgator, userAchievement);
        //TODO
    //    userDAO.updateUserAchievement(receiver, receiverAchievement);
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        PrintWriter printWriter = response.getWriter();
        StringBuffer backMsg = new StringBuffer();
        if( isFinishEvaluate ==true){
            backMsg.append("评价成功");
        }if (isFinishEvaluate == false){
            backMsg.append("评价失败");
        }
        printWriter.print(backMsg.toString());
        printWriter.flush();
        printWriter.close();
    }
}
