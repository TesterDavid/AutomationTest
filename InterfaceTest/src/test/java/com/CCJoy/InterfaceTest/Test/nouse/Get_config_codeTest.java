package com.CCJoy.InterfaceTest.Test.nouse;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.CCJoy.InterfaceTest.Interface_Design.nouse.Get_config_code;

public class Get_config_codeTest {

    public Get_config_codeTest() {
        // TODO Auto-generated constructor stub
    }

    @Test
    @Parameters({ "url1", "url2", "excelPath", "sheetName", "dataIndex" })
    public void Get_config_code_test(String url1, String url2, String excelPath,String tablename, String sheetName, int dataIndex)
            throws Exception {
        Get_config_code test = new Get_config_code();
        test.doget_config_code(url1, url2, excelPath, tablename,sheetName, dataIndex);
    }
}
