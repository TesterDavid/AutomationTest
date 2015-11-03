package com.CCJoy.InterfaceTest.Test.moreTime;

import com.CCJoy.InterfaceTest.Interface_Design.Login_more;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * @TODO:
 * @Author: 邱卫武
 * @Date：2015/10/12
 */
public class Login_moreTest {
    @Test
    @Parameters({ "url", "tablename" })
    public void Login_oneTest(String url, String tablename){
        Login_more test = new Login_more();
        test.login(url,tablename);
    }
}
