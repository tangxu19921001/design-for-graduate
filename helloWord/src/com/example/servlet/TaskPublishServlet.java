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

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by john on 2015/4/27.
 */
public class TaskPublishServlet extends HttpServlet {
    private TaskDetailDAO taskDetailDao = new TaskDetailDAO();
    private User user = new User();
    private UserDAO userDAO = new UserDAO();
    private TaskManageDAO taskManageDAO= new TaskManageDAO();
    private String taskName;
    private String taskContent;
    int userId;
    private int taskMoney;

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)throws ServletException,
            IOException {
        doPost(request,response);

    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)throws ServletException,
            IOException {
        System.out.println("taskPublishServlet");
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
            taskName = jsonObject.getString("taskName");
            taskContent = jsonObject.getString("taskContent");
            taskMoney = jsonObject.getInt("taskMoney");
            userId = jsonObject.getInt("userId");


        } catch (JSONException e) {
            e.printStackTrace();
        }
       /* String taskName = new String(request.getParameter("taskName").getBytes("iso-8859-1"),"utf-8");
        String taskTime = new String(request.getParameter("taskTime").getBytes("iso-8859-1"),"utf-8");
        String taskContent = new String(request.getParameter("taskContent").getBytes("iso-8859-1"),"utf-8");
        String userName = new String(request.getParameter("userName").getBytes("iso-8859-1"),"utf-8");*/


        /*//测试数据：
        taskName = "你好";
        taskContent = "我很好";
        userId = 23;*/
        TaskDetail taskDetail = new TaskDetail(taskName,new Date(),taskContent);
        taskDetail.setTaskMoney(taskMoney);
        taskDetailDao.addTaskDetail(taskDetail);
        int taskId = taskDetailDao.getIdByTime(taskDetail.getTaskTime());
        taskManageDAO.addTaskManage(userId,taskId);
        System.out.println("添加任务");
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        PrintWriter printWriter = response.getWriter();
        String backMsg = "OK";
        printWriter.print(backMsg);
        printWriter.flush();
        printWriter.close();
    }
}
