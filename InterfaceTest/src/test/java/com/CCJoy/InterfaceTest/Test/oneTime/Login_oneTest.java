package com.CCJoy.InterfaceTest.Test.oneTime;

import com.CCJoy.InterfaceTest.Interface_Design.Login_one;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * @Author: 邱卫武
 * @Date：2015/10/12
 */
public class Login_oneTest {
    @Test
    @Parameters({ "url", "tablename" ,"dataIndex"})
    public void Login_oneTest(String url, String tablename, int dataindex){
        Login_one test = new Login_one();
        test.login(url,tablename,dataindex);
    }
}
