package com.example.userSetting.util;

/**
 * Created by Administrator on 2015/1/16.
 */
public class Global {
    /**
     * web URL prefix
     */
    public static String JPUSH_TITLE = "http://api.jpush.cn:8800/v2/push?";

    public static String SERVICE_URL = "http://172.28.185.7:8080/helloword/";

    public static final String SHAREDPREFERENCE_URL = "/data/data/com.example.userSetting/shared_prefs/test.xml";
    //极光推送参数(必填)
    public static String SENDNO = "sendno";
    public static String APP_KEY = "app_key";
    public static String RECEIVER_TYPE = "receiver_type";
    public static String VERIFICATION = "verification_code";
    public static String MSG_TYPE = "msg_type";
    public static String PLATFORM = "platform";
    //内容 String
    public static String MSG_CONTENT = "msg_content";
    public static String KEY_VALUE = "e58e17e58f8b95a746dc73c5";
    public static String APP_MAIN_PASSWORD = "eea9b56f69296d556296f14a";

    //极光推送的通知
    public final static int MSG_NOTIFICATION_RECEIVED= 99;
    //极光推送的信息
    public final static int MSG_MESSAGE_RECEIVED = 98;
    //消息数目更改
    public final static int MSG_COUNT_UPDATE = 42 ;
    //注册
    public final static int MSG_REGISTER = 1;
    //登陆
    public final static int MSG_LOGIN = 2;


    //得到未完成的任务列表
    public final static int MSG_GET_TASK_LIST = 3;
    // 发布任务
    public final static int MSG_PUBLISH_TASK = 4;

    //确认及评价

    public  final  static int MSG_EVALUATE = 5;
    //返回当前用户任务

    public final static int MSG_TASK_DETAIL  = 6;

}
