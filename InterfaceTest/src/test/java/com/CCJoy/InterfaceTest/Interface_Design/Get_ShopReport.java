package com.CCJoy.InterfaceTest.Interface_Design;


import com.CCJoy.InterfaceTest.BaseFrame.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * TODO :
 * Created by 邱卫武 on 2015/10/25.
 */
public class Get_ShopReport {
    RequestUtils request = new RequestUtils();
    CheckPointUtils check = new CheckPointUtils();
    SQLserverUtils sqldata = new SQLserverUtils();
    ReportUtils report = new ReportUtils();

    public void doget_shopreport(String url_login, String tablename_login, int dataIndex_login, String url_get, String tablename_get, int dataIndex_get) {
        JSONObject jsonExpected = sqldata.getData_expected(tablename_get, dataIndex_get);
        // 先取登录的userid跟password
        List<String> logindata = null;
        if (TestCaseDataUtils.isRun(tablename_login, dataIndex_login)) {
            try {
                logindata = request.login_auth(url_login, tablename_login, dataIndex_login);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (TestCaseDataUtils.isRun(tablename_get, dataIndex_get)) {
                String startDateTime = sqldata.getData_oneValue(tablename_get, dataIndex_get, "startDateTime");
                String endDateTime = sqldata.getData_oneValue(tablename_get, dataIndex_get, "endDateTime");
                //执行请求，获取门店营收报告
                JSONObject jsonshopReport = JSONObject.fromString(request.doGet_auth(url_get + "startDateTime=" + startDateTime + "&endDateTime=" + endDateTime, logindata));
                if (jsonshopReport.getString("IsSuccess").equals("true")) {
                    JSONObject jsonData = jsonshopReport.getJSONObject("Data");
                    JSONArray jsonArray = jsonData.getJSONArray("OrdersStatistics");
                    if (TestCaseDataUtils.isCheck(tablename_get, dataIndex_get)) {
                        check.checkString(jsonData.getString("TodayOrderCount"), jsonExpected.getString("TodayOrderCount_expected"));
                        check.checkString(jsonData.getString("AskForGoodsCount"), jsonExpected.getString("AskForGoodsCount_expected"));
                        check.checkString(jsonData.getString("SummationPrice"), jsonExpected.getString("SummationPrice_expected"));
                        check.checkString(jsonArray.getJSONObject(1).getString("PaymethodName"), jsonExpected.getString("PaymethodName_expected"));
                    } else {
                        report.log(jsonData.toString());
                    }
                }
            }
        }
    }
}
