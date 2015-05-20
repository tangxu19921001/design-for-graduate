package com.example.dao;

import com.example.entity.TaskDetail;
import com.example.entity.TaskManage;
import com.example.entity.User;
import com.example.util.HibernateSessionFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by john on 2015/5/8.
 */
public class TaskManageDAO {

    //taskId的任务给出评价。
public boolean addEvaluate(String evaluate,TaskManage taskManage){


        Session s= HibernateSessionFactory.getSession();
        try{
            s.beginTransaction();
            taskManage.setEvaluate(evaluate);
            s.save(taskManage);
            s.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            return false;//出错
        }finally{
            HibernateSessionFactory.closeSession();
        }
        return true;//评价成功

}
    public  ArrayList<TaskManage> getManageById(int userId) {

        Session session = HibernateSessionFactory.getSession();
        session.beginTransaction();
        String sql = "select t from TaskManage t where t.userId = ?";
        Query query = session.createQuery(sql);
        query.setParameter(0, userId);
        List list = query.list();
        ArrayList<TaskManage> arrayList = new ArrayList<TaskManage>();
        if (list.size() != 0) {

            Iterator iterator = list.iterator();

            while (iterator.hasNext()) {

                TaskManage taskManage = (TaskManage) iterator.next();
                arrayList.add(taskManage);


            }
            session.close();

        }
        return arrayList;
    }

    public TaskManage getTaskManageById(int taskManageId){
        Session session = HibernateSessionFactory.getSession();
        session.beginTransaction();
        TaskManage taskManage = (TaskManage)session.get(TaskManage.class,taskManageId);
        session.close();
        return taskManage;

    }

    public  ArrayList<TaskDetail> findTaskDetailByUserId(int userId){
        Session session = HibernateSessionFactory.getSession();
        session.beginTransaction();
        String sql = "select t from TaskManage t where t.userId = ?";
        Query query = session.createQuery(sql);
        query.setParameter(0,userId);
        List list = query.list();
        if (list.size()!= 0){
            ArrayList<Integer> arrayList = new ArrayList<Integer>();

            Iterator iterator = list.iterator();

            while (iterator.hasNext()){

                TaskManage taskDetail = (TaskManage)iterator.next();
                int taskId = taskDetail.getTaskId();
                arrayList.add(taskId);

            }
            session.close();
            return  findTasksById(arrayList);

        }else{
            session.close();
            return null;
        }
    }

    public ArrayList<TaskDetail> findTasksById(ArrayList<Integer>arrayList){
        TaskDetailDAO taskDetailDAO = new TaskDetailDAO();
         ArrayList<TaskDetail> taskDetails = new ArrayList<TaskDetail>();
        for (int i=0;i<arrayList.size();i++){
          taskDetails.add(taskDetailDAO.getTaskDetailById(arrayList.get(i))) ;
        }
        return  taskDetails;
    }

   public void addTaskManage (int userId,int taskId){
       Session session = HibernateSessionFactory.getSession();

      try {
          session.beginTransaction();
          TaskManage taskManage = new TaskManage();
          taskManage.setUserId(userId);
          taskManage.setTaskId(taskId);
          session.save(taskManage);
          session.getTransaction().commit();

      }catch (Exception e){
          e.printStackTrace();
      }finally {
          session.close();
      }

   }
    public int changeReceiverByTaskId(int taskId,int receiverId){
        Session session = HibernateSessionFactory.getSession();
        try {

            session.beginTransaction();
            String sql = "select t from TaskManage t where t.taskId = ?";
            Query query = session.createQuery(sql);
            query.setParameter(0,taskId);
            List list = query.list();
            if (list.size()!= 0){
                Iterator iterator = list.iterator();
                while (iterator.hasNext()){
                    TaskManage taskManage = (TaskManage)iterator.next();
                  taskManage.setReceiverId(receiverId);

                    session.update(taskManage);
                    session.getTransaction().commit();


                }
        }
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }finally {
            session.close();
        }
        return 1;

    }
}
