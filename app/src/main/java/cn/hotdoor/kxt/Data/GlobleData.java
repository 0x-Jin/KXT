package cn.hotdoor.kxt.Data;


import android.content.Context;

import net.tsz.afinal.FinalDb;

import cn.hotdoor.kxt.Activities.MessageActivity;

/**
 * Created by Administrator on 2015/8/4.
 */
public class GlobleData {
    public static FinalDb db;
    public static String FilepathMusic = "mnt/sdcard/KXT_Icon/";
    public static MessageActivity mess;
    public static Context context;
    public static String loginUrl="http://192.168.10.129/api/client/mode/login";
    public static String token="";
    public static String mobile="";

    public static String captchaUrl = "http://192.168.10.129/api/client/mode/getcode";
    public static String getMessage = "http://192.168.10.129/api/client/mode/lists";
    public static String feedback = "http://192.168.10.129/api/client/mode/feedback";

    public static FinalDb db_comment;
}
