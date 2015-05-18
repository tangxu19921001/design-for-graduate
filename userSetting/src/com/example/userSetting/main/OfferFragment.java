package com.example.userSetting.main;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.userSetting.R;
import com.example.userSetting.adapter.MyTaskItemAdapter;
import com.example.userSetting.adapter.OfferRankAdapter;
import com.example.userSetting.adapter.SuperManRankAdapter;
import com.example.userSetting.myTask.TaskDetail;
import com.example.userSetting.parse.ToJsonLogin;
import com.example.userSetting.user.IMsgBack;
import com.example.userSetting.util.AppMessage;
import com.example.userSetting.util.Global;
import com.example.userSetting.util.HttpTask;
import com.example.userSetting.util.TaskThread;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/1/20.
 */
public class OfferFragment extends Fragment implements IMsgBack {
    private static final String Tag = OfferFragment.class.getSimpleName();
    private ListView offerRankList;
    private ListView superRankList;
    private View indexView;
    private TextView offerRankText;
    private TextView superManRankText;
    private LinearLayout describe;
    private int width;
    private float density;
    private int indexPaddingLeft;
    private String msgKey;

    //index下标转换
    private static final int INDEX_NAVIGATION_OFFER = 0;
    private static final int INDEX_NAVIGATION_SUPER = 1;
    private int indexNavigation = INDEX_NAVIGATION_OFFER;
    private TaskThread taskThread = new TaskThread();
    private Handler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflateView = inflater.inflate(R.layout.offer_tab_layout, null);
        offerRankList = (ListView) inflateView.findViewById(R.id.offer_rank_list);
        superRankList = (ListView) inflateView.findViewById(R.id.super_man_rank_list);
        indexView = inflateView.findViewById(R.id.index_view);
        describe = (LinearLayout) inflateView.findViewById(R.id.describe);
        offerRankText = (TextView) inflateView.findViewById(R.id.offer_rank_text);
        superManRankText = (TextView) inflateView.findViewById(R.id.super_man_rank_text);
        initHandler();
        init();
        msgKey = DumpMessage.getInstance().RegistryCallback(OfferFragment.this);
        initList();


        return inflateView;
    }

    private void initHandler() {
        handler = new Handler() {
            @Override
            public void dispatchMessage(Message msg) {
                switch (msg.what) {
                    case Global.MSG_GET_TASK_LIST:
                        ArrayList<TaskDetail> taskDetails = (ArrayList<TaskDetail>) msg.obj;
                        OfferRankAdapter offerRankAdapter = new OfferRankAdapter(getActivity(),taskDetails);
                        offerRankList.setAdapter(offerRankAdapter);

                }
            }
        };
    }

    private void initList() {

        String urlStr = Global.SERVICE_URL + "getUnfinishedTask";
        taskThread.addTask(new HttpTask(urlStr, Global.MSG_GET_TASK_LIST, this));
    }

    private void init() {
        Context con = getActivity();
        DisplayMetrics metric = con.getResources().getDisplayMetrics();
        width = metric.widthPixels;     // 屏幕宽度（像素）
        density = metric.density;      // 屏幕密度（0.75 / 1.0 / 1.5）

        SuperManRankAdapter superManRankAdapter = new SuperManRankAdapter(con);

        superRankList.setAdapter(superManRankAdapter);
        offerRankText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offerRankList.setVisibility(View.VISIBLE);
                superRankList.setVisibility(View.GONE);
                describe.setVisibility(View.VISIBLE);
                indexNavigation = INDEX_NAVIGATION_OFFER;
                initIndex();
            }
        });

        superManRankText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                superRankList.setVisibility(View.VISIBLE);
                offerRankList.setVisibility(View.GONE);
                describe.setVisibility(View.GONE);
                indexNavigation = INDEX_NAVIGATION_SUPER;
                initIndex();
            }
        });

        initIndex();
    }

    private void initIndex() {
        if (indexNavigation == INDEX_NAVIGATION_OFFER) {
            indexPaddingLeft = (int) (width / 2 - 100 * density) / 2;
        } else if (indexNavigation == INDEX_NAVIGATION_SUPER) {
            indexPaddingLeft = (int) ((width / 2 * 3 - 100 * density) / 2);
        }

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) indexView.getLayoutParams();
        params.setMargins(indexPaddingLeft, 0, 0, 0);
        indexView.setLayoutParams(params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (taskThread != null) {
            taskThread.threadDestroy();
        }
        if (msgKey != null) {
            DumpMessage.getInstance().UnRegistryCallback(msgKey);
        }
    }

    @Override
    public void onMsg(AppMessage appMessage) {
        Log.i(Tag, appMessage.toString());
        int msgType = appMessage.getMsgType();
        //start 注册
        if (msgType == Global.MSG_GET_TASK_LIST) {
            ToJsonLogin toJsonLogin = new ToJsonLogin(appMessage.getMsg());
            ArrayList<TaskDetail> taskDetails = toJsonLogin.getUnFinishedTask();
            Message successMsg = handler.obtainMessage();

            successMsg.what = Global.MSG_GET_TASK_LIST;
            successMsg.obj = taskDetails;
            handler.sendMessage(successMsg);

        }
    }
}