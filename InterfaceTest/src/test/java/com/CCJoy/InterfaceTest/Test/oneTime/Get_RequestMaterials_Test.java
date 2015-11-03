package com.CCJoy.InterfaceTest.Test.oneTime;

import com.CCJoy.InterfaceTest.Interface_Design.Get_RequestMaterials;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * TODO:
 *
 * @Author: 邱卫武
 * @Date：2015/10/23
 */
public class Get_RequestMaterials_Test {

    public Get_RequestMaterials_Test() {
    }

    @Test
    @Parameters({ "url_login", "tablename_login", "dataIndex_login", "url_get", "tablename_get","dataIndex_get" })
    public void get_requestmaterials(String url_login,String tablename_login,int dataIndex_login,String url_get,String tablename_get,int dataIndex_get){
        Get_RequestMaterials test = new Get_RequestMaterials();
        test.doget_requestmaterials(url_login, tablename_login, dataIndex_login, url_get, tablename_get, dataIndex_get);
    }
}
