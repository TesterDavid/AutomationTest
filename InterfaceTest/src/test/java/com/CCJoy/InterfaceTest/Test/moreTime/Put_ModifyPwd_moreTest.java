package com.CCJoy.InterfaceTest.Test.moreTime;

import com.CCJoy.InterfaceTest.Interface_Design.Put_ModifyPwd_more;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * TODO:
 * @Author: 邱卫武
 * @Date：2015/10/23
 */
public class Put_ModifyPwd_moreTest {
    public Put_ModifyPwd_moreTest() {
    }

    @Test
    @Parameters({ "url_login", "tablename_login", "dataIndex","url_put","tablename_put" })
    public void put_modifyPwd_moreTest(String url_login,String tablename_login,int dataIndex,String url_put,String tablename_put){
        Put_ModifyPwd_more test = new Put_ModifyPwd_more();
        test.doput_ModifyPwd_more(url_login,tablename_login,dataIndex,url_put,tablename_put);
    }
}
