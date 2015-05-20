package com.example.dao;

import com.example.entity.User;
import com.example.util.HibernateSessionFactory;
import org.hibernate.Query;
import org.hibernate.Session;


import java.util.Iterator;
import java.util.List;


/**
 * tx
 * Created by Administrator on 2015/3/13.
 */
public class UserDAO {

    //判断账号密码的正确性
    public User doLogin(String name, String password){
        Session s = HibernateSessionFactory.getSession();
        s.beginTransaction();

        String sql = "select u from com.example.entity.User u where u.name='" + name + "' and u.password = '" + password +"'";
        System.out.println(name + ":" + password);
        Query q = s.createQuery(sql);
        List list = q.list();
        Iterator it = list.iterator();
        System.out.println("查询有"+list.size()+"个结果用户");
        s.close();
        while(it.hasNext()){
            User u = (User)it.next();
            System.out.println(u.getName() + "|" + u.getPassword() + "|" + u.getUserId());
            return u;
        }
        return null;
    }

    public  int addUser(String name ,String password){

        if (UserNameExists(name)== false){
            Session s=HibernateSessionFactory.getSession();
            try{
                s.beginTransaction();
                User u=new User();
                u.setName(name);
                u.setPassword(password);
                s.save(u);
                s.getTransaction().commit();
            }catch(Exception e){
                e.printStackTrace();
                return -1;//出错
            }finally{
                HibernateSessionFactory.closeSession();
            }
            return 1;//注册成功
        }else{

            //存在相同的帐号
            return 0;
        }

    }

    private boolean UserNameExists(String name){
        User u = getUserByName(name);
        if (u == null){
            return  false;
        }
        return true;
    }
    //通过用户名获取用户数据
    private  User getUserByName(String name){
        Session s = HibernateSessionFactory.getSession();

        s.beginTransaction();
        String sql="select u from User u where u.name='"+name+"'";
        Query q=s.createQuery(sql);
        User u;
        List list=q.list();
        if(list.size()!=0){
            Iterator it=list.iterator();
            u=(User)it.next();
        }
        else{
            u=null;
        }
        s.close();
        System.out.print(""+u);
        return u;
    }

    //通过userId获取user
    public User getUserById(int userId){
        Session session = HibernateSessionFactory.getSession();
        session.beginTransaction();
        User user = (User)session.get(User.class,userId);
        session.close();
        return user;
    }

    public void updateUserMoney(User user,int money){
        Session session = HibernateSessionFactory.getSession();
        session.beginTransaction();
        user.setMoney(money);
        session.update(user);
        session.getTransaction().commit();
        session.close();
    }
    public void updateUserAchievement(User user,int achievement){
        Session session = HibernateSessionFactory.getSession();
        session.beginTransaction();
        user.setAchievementPoint(achievement);
        session.update(user);
        session.getTransaction().commit();
        session.close();
    }
}
