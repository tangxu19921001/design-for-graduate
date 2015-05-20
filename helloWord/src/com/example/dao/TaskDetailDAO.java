package com.example.dao;

import com.example.entity.TaskDetail;
import com.example.util.HibernateSessionFactory;
import com.example.util.ToJson;
import com.example.util.ToJsonImpl;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by john on 2015/4/28.
 */
public class TaskDetailDAO {
    ToJson toJson = new ToJsonImpl();
    public void addTaskDetail(TaskDetail taskDetail){
        Session session = HibernateSessionFactory.getSession();
        try{
            System.out.println(taskDetail.getTaskContent());
            session.beginTransaction();
            taskDetail.setIsFinished(false);
            taskDetail.setIsReceived(false);
            session.save(taskDetail);
            session.getTransaction().commit();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

    }

  public int markAcceptTask(int taskId){
   TaskDetail taskDetail =   getTaskDetailById(taskId);
    Session session = HibernateSessionFactory.getSession();
      try{
          session.beginTransaction();
          taskDetail.setIsReceived(true);
          session.update(taskDetail);
          session.getTransaction().commit();

      }catch (Exception e){
          e.printStackTrace();
          return -1;
      }finally {
          session.close();
      }
return 1;
}

    public String getUnfinishedTask(){
        Session session = HibernateSessionFactory.getSession();
        session.beginTransaction();
        String sql = "select t from TaskDetail t where t.isFinished = false";
        Query query = session.createQuery(sql);
        List list = query.list();
        if (list.size()!= 0){
            List<TaskDetail>taskDetails = new ArrayList<TaskDetail>();
            Iterator iterator = list.iterator();
            int i=0;
            while (iterator.hasNext()){
                TaskDetail taskDetail = (TaskDetail)iterator.next();
                taskDetails.add(taskDetail);

            }
            return  "\n"+ toJson.toJson(taskDetails);

        }else{
            return "";
        }
    }
    public void finishTask(TaskDetail taskDetail,boolean isFinished){
        Session session = HibernateSessionFactory.getSession();
        session.beginTransaction();
        taskDetail.setIsFinished(isFinished);
        session.update(taskDetail);
        session.getTransaction().commit();
        session.close();
    }
    //通过taskId获取taskDetail
    public TaskDetail getTaskDetailById(int taskId){
        Session session = HibernateSessionFactory.getSession();
        session.beginTransaction();
        TaskDetail taskDetail = (TaskDetail)session.get(TaskDetail.class,taskId);
        session.close();
        return taskDetail;

    }
    public int getIdByTime(Date time){
        Session session = HibernateSessionFactory.getSession();
        session.beginTransaction();
        String sql="SELECT t.taskId from TaskDetail t where t.taskTime =?";
        Query query = session.createQuery(sql);
        query.setParameter(0,time);
        List list = query.list();
        if (list.size()!=0){
            int taskId = (Integer)list.get(0);
            return taskId;
        }else{
            return -1;
        }

    }
}
