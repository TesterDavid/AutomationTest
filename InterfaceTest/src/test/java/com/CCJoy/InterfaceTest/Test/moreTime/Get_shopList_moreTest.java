package com.CCJoy.InterfaceTest.Test.moreTime;

import com.CCJoy.InterfaceTest.Interface_Design.Get_shopList_more;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * @TODO:
 * @Author: 邱卫武
 * @Date：2015/10/16
 */
public class Get_shopList_moreTest {
    public Get_shopList_moreTest() {
        // TODO Auto-generated constructor stub
    }

    @Test
    @Parameters({ "url_login", "url_get", "tablename", "tablename_login"})
    public void Get_shopList_test(String url_login, String url_get, String tablename, String tablename_login)  {
        Get_shopList_more test = new Get_shopList_more();
        test.doGet_shopList_more(url_login, url_get, tablename, tablename_login);
    }
}
