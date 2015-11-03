package com.CCJoy.InterfaceTest.Test.oneTime;

import com.CCJoy.InterfaceTest.Interface_Design.Post_Bosslogin_one;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * @Author: 邱卫武
 * @Date：2015/10/20
 */
public class Post_Bosslogin_oneTest {
    @Test
    @Parameters({ "url", "tablename" ,"dataIndex"})
    public void Bosslogin_oneTest (String url, String tablename, int dataindex){
        Post_Bosslogin_one test = new Post_Bosslogin_one();
        test.dopost_Bosslogin_one(url,tablename,dataindex);
    }
}
