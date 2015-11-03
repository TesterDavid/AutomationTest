package com.CCJoy.InterfaceTest.Test.oneTime;

import com.CCJoy.InterfaceTest.Interface_Design.Post_Create_order_one;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * @TODO:
 * @Author: 邱卫武
 * @Date：2015/9/29
 */
public class Create_order_oneTest {
    public Create_order_oneTest() {
        // TODO Auto-generated constructor stub
    }

    @Test
    @Parameters({ "url1", "url2", "tablename","tablename2", "dataindex","dataindex_login" })
    public void create_order_test(String url1, String url2,  String tablename, String tablename2, int dataindex, int dataindex_login) throws Exception {
        Post_Create_order_one test = new Post_Create_order_one();
        test.doCreate_order(url1, url2,tablename,tablename2,dataindex, dataindex_login);
    }
}
