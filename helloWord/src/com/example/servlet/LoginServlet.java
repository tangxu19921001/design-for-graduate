package com.example.servlet;

import com.example.dao.UserDAO;
import com.example.entity.User;
import com.example.util.ToJson;
import com.example.util.ToJsonImpl;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2015/3/13.
 */
public class LoginServlet extends javax.servlet.http.HttpServlet {
    UserDAO userDAO = new UserDAO();
    ToJson toJson = new ToJsonImpl();
    @Override
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
//        response.getWriter().print("xxx");
        System.out.print("用户登录帐号：:");
        String name = new String (request.getParameter("name").getBytes("iso-8859-1"),"utf-8").trim();
        String password =  request.getParameter("password").trim();
        System.out.println( name + "密码：" + password);
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String msg = null ;
        //TODO 将时间，语言等序列化
        User result = userDAO.doLogin(name,password);
        if ( result != null){

            msg = toJson.toAchievementJson(result);


           /* if (result.getInfo()!=null){

            }*/

        }else{
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("isSuccess",0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            msg = jsonObject.toString();
        }
        out.print(msg);
        out.flush();
        out.close();
    }
    @Override
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        response.getWriter().print("yyy");
    }
}
