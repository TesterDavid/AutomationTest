package com.CCJoy.InterfaceTest.Test.oneTime;

import com.CCJoy.InterfaceTest.Interface_Design.Get_ShopReport;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * TODO:
 *
 * Created by 邱卫武 on 2015/10/25.
 */
public class Get_ShopReportTest {
    public Get_ShopReportTest() {
    }

    @Test
    @Parameters({"url_login", "tablename_login", "dataIndex_login", "url_get", "tablename_get", "dataIndex_get"})
    public void get_shopReportTest(String url_login, String tablename_login, int dataIndex_login, String url_get, String tablename_get, int dataIndex_get) {
        Get_ShopReport test = new Get_ShopReport();
        test.doget_shopreport(url_login, tablename_login, dataIndex_login, url_get, tablename_get, dataIndex_get);
    }
}
