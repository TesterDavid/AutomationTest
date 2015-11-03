package com.CCJoy.InterfaceTest.Test.nouse;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.CCJoy.InterfaceTest.Interface_Design.nouse.Get_user_configs;

public class Get_user_configsTest {

    public Get_user_configsTest() {
        // TODO Auto-generated constructor stub
    }

    @Test
    @Parameters({ "url1", "url2", "excelPath", "sheetName", "dataIndex" })
    public void Get_user_configs_test(String url1, String url2, String excelPath,String tablename, String sheetName, int dataIndex) throws Exception{
        Get_user_configs test = new Get_user_configs();
        test.doget_user_config(url1, url2, excelPath, sheetName, tablename,dataIndex);
    }
}
