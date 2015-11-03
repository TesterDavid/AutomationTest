package com.CCJoy.InterfaceTest.BaseFrame;

import java.io.*;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * @TODO: 获取配置文件中的值
 * @Author: 邱卫武
 * @Date：2015/10/9
 */
public class ConfigUtils {

    private  static ResourceBundle rb = ResourceBundle.getBundle("config");

    //数据库地址
    public final static String DBURL = rb.getString("DBURL");

    //数据库名称
    public  final static String DATABASENAME = rb.getString("DATABASENAME");

    //数据库登录用户名
    public final static String DATABASEUSERNAME = rb.getString("DATABASEUSERNAME");

    //数据库登录密码
    public final static String DATABASEPASSWORD = rb.getString("DATABASEPASSWORD");

    //接口请求主机地址
    public  final static String HOST = rb.getString("HOST");

    //HOST端口
    public  final static String PORT = rb.getString("PORT");

    public static String getPorperty(String key) {
        Properties pps = new Properties();
        try {
            InputStream in = Object.class.getResourceAsStream("/config.properties");
            pps.load(in);
            String value = pps.getProperty(key);
            return value;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
