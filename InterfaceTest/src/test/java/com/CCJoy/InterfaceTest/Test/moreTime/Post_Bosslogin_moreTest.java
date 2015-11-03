package com.CCJoy.InterfaceTest.Test.moreTime;

import com.CCJoy.InterfaceTest.Interface_Design.Post_Bosslogin_more;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * @TODO:
 * @Author: 邱卫武
 * @Date：2015/10/21
 */
public class Post_Bosslogin_moreTest {
    @Test
    @Parameters({"url", "tablename"})
    public void dopost_Bosslogin_moreTest (String url, String tablename) {
        Post_Bosslogin_more test = new Post_Bosslogin_more();
        test.dopost_Bosslogin_more(url, tablename);
    }
}
