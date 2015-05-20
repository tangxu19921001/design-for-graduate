package com.example.servlet;

import com.example.dao.TaskManageDAO;
import com.example.dao.UserDAO;
import com.example.entity.TaskManage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by john on 2015/4/14.
 */
public class RegisterServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();
    private TaskManageDAO taskManageDAO = new TaskManageDAO();
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)throws ServletException,
            IOException {
        System.out.print("连接好服务器");
        //get请求的name参数
        String name = new String (request.getParameter("name").getBytes("iso-8859-1"),"utf-8").trim();
        String password = new String (request.getParameter("password").getBytes("iso-8859-1"),"utf-8").trim();
        System.out.println( name + "_|" + password);
//服务器返回信息
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();

        String msg;

        int result= userDAO.addUser(name, password);

        if (result == 1){
            msg = "注册成功";
        }else if(result == 0){
            msg = "相同帐号存在";
        }else {
            msg = "注册失败";
        }
        out.print(msg);
        out.flush();
        out.close();
    }
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)throws ServletException,
            IOException{

    }

}
