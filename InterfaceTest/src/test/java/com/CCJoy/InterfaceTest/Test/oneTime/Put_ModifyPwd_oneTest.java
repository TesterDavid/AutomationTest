package com.CCJoy.InterfaceTest.Test.oneTime;

import com.CCJoy.InterfaceTest.Interface_Design.Put_ModifyPwd_one;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * @Author: 邱卫武
 * @Date：2015/10/21
 */
public class Put_ModifyPwd_oneTest {
    @Test
    @Parameters({ "url_login", "tablename_login" ,"url_put","tablename_put","dataIndex_login","dataIndex_put"})
    public void doPut_ModifyPwd_oneTest(String url_login, String tablename_login, String url_put, String tablename_put, int dataIndex_login, int dataIndex_put){
        Put_ModifyPwd_one test = new Put_ModifyPwd_one();
       test.doput_ModifyPwd_one(url_login,tablename_login,url_put,tablename_put,dataIndex_login,dataIndex_put);
    }
}
