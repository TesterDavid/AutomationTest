package com.CCJoy.InterfaceTest.Test.oneTime;

import com.CCJoy.InterfaceTest.Interface_Design.Get_shopList_one;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * TODO: 获取商户门店数量并验证门店名称测试类
 *
 * @Author: 邱卫武
 * @Date：2015/9/24
 */
public class Get_shopList_oneTest {

    public Get_shopList_oneTest() {
        // TODO Auto-generated constructor stub
    }

    @Test
    @Parameters({ "url_login", "url_get", "tablename", "tablename_login", "dataIndex_login","dataIndex_get" })
    public void Get_shopList_test(String url_login, String url_get, String tablename, String tablename_login, int dataIndex_login, int dataIndex_get)  {
        Get_shopList_one test = new Get_shopList_one();
        test.Get_shopList_one(url_login, url_get, tablename, tablename_login, dataIndex_login, dataIndex_get);
    }
}
