package com.CCJoy.InterfaceTest.Test.nouse;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.CCJoy.InterfaceTest.Interface_Design.nouse.LogoutInterface;

public class LogoutInterfaceTest {

    public LogoutInterfaceTest() {
        // TODO Auto-generated constructor stub
    }

    @Test
    @Parameters({ "url1", "url2","excelPath", "sheetName" ,"dataIndex"})
    public void logoutTest(String url1, String url2,String excelPath,String tablename, String sheetName,int dataIndex) throws Exception{
        LogoutInterface logouttest = new LogoutInterface();
        logouttest.logout(url1,url2, excelPath, sheetName,tablename,dataIndex);
    }
}
