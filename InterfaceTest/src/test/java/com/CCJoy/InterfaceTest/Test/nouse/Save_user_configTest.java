package com.CCJoy.InterfaceTest.Test.nouse;

import com.CCJoy.InterfaceTest.Interface_Design.nouse.Save_user_config;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * @TODO:
 * @Author: 邱卫武
 * @Date：2015/9/22
 */
public class Save_user_configTest {

    public Save_user_configTest() {
        // TODO Auto-generated constructor stub
    }

    @Test
    @Parameters({"url1", "url2", "url3", "excelPath", "sheetName", "dataIndex"})
    public void Get_config_code_test(String url1, String url2, String url3,String tablename, String excelPath, String sheetName, int dataIndex)
            throws Exception {
        Save_user_config test = new Save_user_config();
        test.doSave_user_config(url1, url2, url3, excelPath, tablename,sheetName, dataIndex);
    }
}
