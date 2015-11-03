package com.CCJoy.InterfaceTest.Interface_Design;

import com.CCJoy.InterfaceTest.BaseFrame.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * TODO: 获取请货列表测试类
 *
 * @Author: 邱卫武
 * @Date：2015/10/23
 */
public class Get_RequestMaterials {
    RequestUtils request = new RequestUtils();
    CheckPointUtils check = new CheckPointUtils();
    ReportUtils report = new ReportUtils();
    SQLserverUtils sqldata = new SQLserverUtils();

    public Get_RequestMaterials() {

    }

    public void doget_requestmaterials(String url_login, String tablename_login, int dataIndex_login, String url_get, String tablename_get, int dataIndex_get) {
        List<String> logindata = null;
        String org_id = sqldata.getData_oneValue(tablename_get, dataIndex_get, "org_id");
        if (TestCaseDataUtils.isRun(tablename_login, dataIndex_login)) {
            try {
                //获取登录userid跟token
                logindata = request.login_auth(url_login, tablename_login, dataIndex_login);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //判断用例是否执行
            if (TestCaseDataUtils.isRun(tablename_get, dataIndex_get)) {
                //执行用例
                JSONObject jsonResult = JSONObject.fromString(request.doGet_auth(url_get + org_id, logindata));
                //获取请货单列表
                JSONArray dataArry = jsonResult.getJSONArray("Data");
                int RequestMaterialsNum = dataArry.length();
                if (TestCaseDataUtils.isCheck(tablename_get, dataIndex_get)) {
                    check.checkString(Integer.toString(RequestMaterialsNum), sqldata.getData_oneValue(tablename_get, dataIndex_get, "RequestMaterialsNum"));
                } else {
                    report.log("请货单数量为" + dataArry.length());
                }
            }
        }
    }
}
