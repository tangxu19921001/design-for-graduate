package com.example.servlet;

import com.example.dao.TaskDetailDAO;
import com.example.dao.TaskManageDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by john on 2015/5/19.
 */
public class ReceiverAcceptServlet extends HttpServlet{
    TaskManageDAO taskManageDAO = new TaskManageDAO();
    TaskDetailDAO taskDetailDAO = new TaskDetailDAO();
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)throws ServletException,
            IOException {

        int taskId =Integer.parseInt( request.getParameter("taskId").trim());
        int receiverId =Integer.parseInt( request.getParameter("receiverId").trim());
        System.out.println(taskId+receiverId);
        int isSuccess = taskManageDAO.changeReceiverByTaskId(taskId,receiverId);
        int isMark =taskDetailDAO.markAcceptTask(taskId);
        System.out.println("isMark"+isMark);
        PrintWriter writer = response.getWriter();
        System.out.println("isSuccess"+isSuccess);
        writer.print(isSuccess);
        writer.flush();
        writer.close();

    }
}
