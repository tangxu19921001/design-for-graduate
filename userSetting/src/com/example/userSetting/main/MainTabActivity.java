package com.example.userSetting.main;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import cn.jpush.android.api.JPushInterface;
import com.example.userSetting.R;
import com.example.userSetting.db.DBManager;
import com.example.userSetting.db.NewsMessage;
import com.example.userSetting.io.Setting;
import com.example.userSetting.user.IMsgBack;
import com.example.userSetting.user.UserFragment;
import com.example.userSetting.util.AppMessage;
import com.example.userSetting.util.Global;
import com.example.userSetting.util.SysApplication;

import java.util.ArrayList;

/**
 * main activity
 * Created by Administrator on 2015/1/20.
 */
public class MainTabActivity extends FragmentActivity implements IMsgBack {
    private String Tag = "MainTabActivity";

    //定义FragmentTabHost对象
    private FragmentTabHost mTabHost;
    TextView newsNumber;
    Handler handler;
    private int count = 0;
    private String msgKey;

    //定义一个布局
    private LayoutInflater layoutInflater;
    private DBManager dbManager;


    //
    private static final int FLAG_HANDLE_MSG = 1;
    private int returnFragment = -1;

    //定义数组来存放Fragment界面
    private Class fragmentArray[] = {OfferFragment.class, DemandFragment.class, NewsFragment.class, UserFragment.class};

    //定义数组来存放按钮图片
    private int mImageViewArray[] = {R.drawable.tab_offer_btn, R.drawable.tab_demand_btn, R.drawable.tab_message_btn,
            R.drawable.user_tab_btn};

    //Tab选项卡的文字
    private String textViewArray[] = {"悬赏榜", "发任务", "看消息", "登录"};
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.userSetting.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tab);
        Setting.init(MainTabActivity.this);
        JPushInterface.init(this);
        JPushInterface.setDebugMode(true);
        msgKey = DumpMessage.getInstance().RegistryCallback(this);
        SysApplication.getInstance().addActivity(this);
        dbManager = new DBManager(this);
        ArrayList<NewsMessage> newsContentList = new ArrayList<NewsMessage>();
        newsContentList = dbManager.query();
        count = newsContentList.size();

        initView();
        initHandler();
       /* initFilter();*/
        Message handleFlag = handler.obtainMessage();
        handleFlag.what = FLAG_HANDLE_MSG;
        handler.sendMessage(handleFlag);
    }

    /*private void initFilter(){
        MessageReceiver messageReceiver = new MessageReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MESSAGE_RECEIVED_ACTION);
        intentFilter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        registerReceiver(messageReceiver,intentFilter);
    }

 public class MessageReceiver extends BroadcastReceiver{

     @Override
     public void onReceive(Context context, Intent intent) {
         Log.i(Tag,intent.getAction().toString());
         if ( MESSAGE_RECEIVED_ACTION == intent.getAction()){
             String message = intent.getStringExtra(KEY_MESSAGE);
             String extra = intent.getStringExtra(KEY_EXTRAS);
             String Jmessage = intent.getStringExtra(JPushInterface.EXTRA_MESSAGE);
             Log.i("....","message:"+ message + "extra"+extra+"Jmessage"+Jmessage);

         }
     }
 }*/
    //TODO 做完这个深度理解activity的生命周期
    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        returnFragment = intent.getIntExtra("fragment", 0);
        mTabHost.setCurrentTab(returnFragment);
    }

    /**
     * 初始化组件
     */
    private void initView() {
        //实例化布局对象
        layoutInflater = LayoutInflater.from(this);

        //实例化TabHost对象，得到TabHost
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        //得到fragment的个数
        int count = fragmentArray.length;

        for (int i = 0; i < count; i++) {
            //为每一个Tab按钮设置图标、文字和内容

            TabHost.TabSpec tabSpec;
            if (i == 2) {
                tabSpec = mTabHost.newTabSpec(textViewArray[i]).setIndicator(getSpecialTemView());
            } else {
                tabSpec = mTabHost.newTabSpec(textViewArray[i]).setIndicator(getTabItemView(i));
            }


            //将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, fragmentArray[i], null);
            //设置Tab按钮的背景
            mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.color.navigate_bg);
            if (returnFragment != -1) {
                mTabHost.setCurrentTab(returnFragment);
            } else {
                mTabHost.setCurrentTab(0);
            }
        }
    }

    private void initHandler() {
        handler = new Handler() {
            @Override
            public void dispatchMessage(Message msg) {
                switch (msg.what) {
                    case FLAG_HANDLE_MSG:

                        setFlag();
                        break;
                }
            }
        };
    }

    private void setFlag() {
        if (count == 0) {
            newsNumber.setVisibility(View.GONE);
        }
        newsNumber.setText(String.valueOf(count));
    }

    private View getSpecialTemView() {
        View view = layoutInflater.inflate(R.layout.special, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        newsNumber = (TextView) view.findViewById(R.id.newsNumber);
        imageView.setImageResource(mImageViewArray[2]);

        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(textViewArray[2]);

        return view;

    }

    /**
     * 给Tab按钮设置图标和文字
     */
    private View getTabItemView(int index) {
        View view = layoutInflater.inflate(R.layout.tab_item_view, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageResource(mImageViewArray[index]);

        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(textViewArray[index]);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (msgKey != null) {
            DumpMessage.getInstance().UnRegistryCallback(msgKey);
        }
        SysApplication.getInstance().exit();
    }
   /* @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0){
            AlertDialog.Builder alertbBuilder=new AlertDialog.Builder(this);
            alertbBuilder.setTitle("真的要离开？").setMessage("再按一次退出校园威客").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //结束这个Activity
                    int nPid = android.os.Process.myPid();
                    android.os.Process.killProcess(nPid);
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }).create();
            alertbBuilder.show();
        }
        return true;
    }*/


    @Override
    public void onMsg(AppMessage appMessage) {
        //TODO
        int msgType = appMessage.getMsgType();
        if (msgType == Global.MSG_COUNT_UPDATE){
            count++;
            Message handleFlag = handler.obtainMessage();
            handleFlag.what = FLAG_HANDLE_MSG;
            handler.sendMessage(handleFlag);
        }

    }
}

