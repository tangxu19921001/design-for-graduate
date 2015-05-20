package com.example.servlet;

import com.example.dao.TaskDetailDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by john on 2015/5/1.
 */
public class GetUnfinishedTaskServlet extends HttpServlet{
    TaskDetailDAO taskDetailDAO = new TaskDetailDAO();
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)throws ServletException,
            IOException {
        String msg = taskDetailDAO.getUnfinishedTask();
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        PrintWriter out=response.getWriter();
        out.print(msg);
        out.flush();
        out.close();

    }


}
