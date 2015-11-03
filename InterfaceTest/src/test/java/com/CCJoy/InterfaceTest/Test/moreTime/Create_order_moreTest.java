package com.CCJoy.InterfaceTest.Test.moreTime;

import com.CCJoy.InterfaceTest.Interface_Design.Post_Create_order_more;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * @TODO:
 * @Author: 邱卫武
 * @Date：2015/10/15
 */
public class Create_order_moreTest {

    public Create_order_moreTest() {
        // TODO Auto-generated constructor stub
    }
    @Test
    @Parameters({ "url1", "url2", "tablename","tablename_login","dataindex_login" })
    public void create_order_moreTest(String url1, String url2,  String tablename, String tablename_login, int dataindex_login) throws Exception {
        Post_Create_order_more test = new Post_Create_order_more();
        test.doCreate_order_more(url1, url2,tablename,tablename_login, dataindex_login);
    }
}
