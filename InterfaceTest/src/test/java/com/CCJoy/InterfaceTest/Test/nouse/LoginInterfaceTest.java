package com.CCJoy.InterfaceTest.Test.nouse;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.CCJoy.InterfaceTest.Interface_Design.nouse.LoginInterface;

public class LoginInterfaceTest {

    public LoginInterfaceTest() {
        // TODO Auto-generated constructor stub
    }


    @Test
    @Parameters({ "url", "excelPath", "sheetName" ,"dataIndex"})
    public void loginTest(String url ,String excelPath,String tablename, String sheetName,int dataIndex) throws Exception{
        LoginInterface logintest = new LoginInterface();
        logintest.login(url, excelPath, sheetName,tablename,dataIndex);
    }
}
