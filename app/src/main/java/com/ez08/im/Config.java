package com.ez08.im;

import com.ez08.im.model.User;

/**
 * Created by Administrator on 2016/4/12.
 */
public class Config {
    //网络地址配置
    public static String CMS_NEWSLIST_URL = "http://7xsu4i.com2.z0.glb.qiniucdn.com";
    public static String USER_URL =  "http://192.168.76.214/drupal7";
    public static String FRIEND_URL = "http://7xtdd8.com1.z0.glb.clouddn.com";
    public static String IMAGE_URL = "http://192.168.76.214/drupal7/sites/default/files";

    public static User user = null;
    public static String cookie = null;
    public static String uid = null;
    public static String token = null;

    //cell 类型
    public final static int NORMAL = 1;
    public final static int BASEINFO = 2;
    ////
}
