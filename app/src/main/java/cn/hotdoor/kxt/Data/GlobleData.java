package cn.hotdoor.kxt.Data;


import android.content.Context;

import net.tsz.afinal.FinalDb;

import cn.hotdoor.kxt.Activities.MessageActivity;

/**
 * Created by Administrator on 2015/8/4.
 */
public class GlobleData {
    public static FinalDb db;
    public static String FilepathMusic = "mnt/sdcard/KXT_Pic/";
    public static MessageActivity mess;
    public static Context context;
    String baseUrl="";
    String loginUrl="";
    public static String captchaUrl="http://192.168.10.129/api/client/mode/getcode";

}
