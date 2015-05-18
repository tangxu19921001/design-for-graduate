package com.example.userSetting.main;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.userSetting.R;
import com.example.userSetting.adapter.MyTaskItemAdapter;
import com.example.userSetting.io.Setting;
import com.example.userSetting.myTask.EvaluateActivity;
import com.example.userSetting.myTask.TaskDetail;
import com.example.userSetting.myTask.TaskDetailActivity;
import com.example.userSetting.parse.ToJsonLogin;
import com.example.userSetting.user.IMsgBack;
import com.example.userSetting.util.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/1/20.
 */
public class DemandFragment extends Fragment  implements IMsgBack{
    private final static String Tag = DemandFragment.class.getSimpleName();
    private Context context;
    private String key;
    private TaskThread taskThread = new TaskThread();
    private Handler handler;
    private   ListView myTaskList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        key = DumpMessage.getInstance().RegistryCallback(this);
        View inflateView = inflater.inflate(R.layout.demand_tab_layout, null);

        initView(inflateView);
        initHandler();
        initList();
        return inflateView;
    }
    private void initHandler() {
        handler = new Handler() {
            @Override
            public void dispatchMessage(Message msg) {
                switch (msg.what) {
                    case  Global.MSG_TASK_DETAIL:
                        ArrayList<TaskDetail>taskDetails =(ArrayList<TaskDetail>) msg.obj;
                     MyTaskItemAdapter myTaskItemAdapter = new MyTaskItemAdapter(context,taskDetails);
                     myTaskList.setAdapter(myTaskItemAdapter);
                        myTaskList.setOnItemClickListener(new OnItemChildClickListener());

                }
            }
        };
    }

    private class OnItemChildClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            //position =i;
            Intent intent = new Intent(context, EvaluateActivity.class);
            intent.putExtra("position",i);
            startActivity(intent);
        }
    }
    private void initView(View inflateView) {
        inflateView.findViewById(R.id.back).setVisibility(View.GONE);
        TextView title = (TextView) inflateView.findViewById(R.id.title);
        title.setText(R.string.my_task);
        myTaskList = (ListView) inflateView.findViewById(R.id.my_task_list);

      /*  MyTaskItemAdapter myTaskItemAdapter = new MyTaskItemAdapter(context);
        myTaskList.setAdapter(myTaskItemAdapter);*/
        TextView giveTask = (TextView) inflateView.findViewById(R.id.give_task);
        giveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TaskDetailActivity.class);
                startActivity(intent);
            }
        });
    }
     private void  initList(){
         if (Setting.getInstance().getUserName() !=null&&Setting.getInstance().getPassword()!=null){
             String urlStr = Global.SERVICE_URL + "login?";
             StringBuffer buffer = new StringBuffer(urlStr);
             try {
                 buffer.append("name=").append(URLEncoder.encode(Setting.getInstance().getUserName(), "utf-8")).append("&password=").append(Setting.getInstance().getPassword());
             } catch (UnsupportedEncodingException e1) {
                 e1.printStackTrace();
             }
             urlStr = buffer.toString();
             taskThread.addTask(new HttpTask(urlStr, Global.MSG_TASK_DETAIL,this));
         }

     }
    @Override
    public void onDestroy(){
        Log.i(Tag, "onDestroy-->");
        super.onDestroy();
        if (taskThread != null) {
            taskThread.threadDestroy();
        }

        DumpMessage.getInstance().UnRegistryCallback(key);
    }
    @Override
    public void onMsg(AppMessage appMessage) {
        Log.i(Tag,appMessage.toString());
        int  msgType = appMessage.getMsgType();
        //start 注册
        if (msgType == Global.MSG_TASK_DETAIL){
            ToJsonLogin toJsonLogin = new ToJsonLogin(appMessage.getMsg());
            ArrayList<TaskDetail> taskDetails = toJsonLogin.parseTaskDetail();
            Message successMsg = handler.obtainMessage();
            successMsg.what =  Global.MSG_TASK_DETAIL;
            successMsg.obj = taskDetails;
            handler.sendMessage(successMsg);
        }
    }
}
