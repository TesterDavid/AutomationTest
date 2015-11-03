package com.CCJoy.InterfaceTest.Interface_Design;

import com.CCJoy.InterfaceTest.BaseFrame.*;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * @TODO: 创建订单接口
 * @Author: 邱卫武
 * @Date：2015/9/29
 */
public class Post_Create_order_one {
    RequestUtils request = new RequestUtils();
    SQLserverUtils sqldata = new SQLserverUtils();
    CheckPointUtils check = new CheckPointUtils();
    DataUtils data = new DataUtils();
    ReportUtils report = new ReportUtils();

    public Post_Create_order_one() {
        // TODO Auto-generated constructor stub
    }

    public void doCreate_order(String url1, String url2, String tablename, String tablename2, int dataindex, int dataindex_login) throws Exception {
        String stringResult;
        List<String> result = null;
        JSONObject jsonObject = sqldata.getData_pamas(tablename, dataindex);
        if (TestCaseDataUtils.isRun(tablename2, dataindex_login)) {
            result = request.login_auth(url1, tablename2, dataindex_login);
            if (result != null) {
                if (TestCaseDataUtils.isRun(tablename, dataindex)) {
                    stringResult = request.doPost_auth(url2, result, jsonObject);
                    report.log(stringResult);
                    JSONObject jsonresult = JSONObject.fromString(stringResult);
                    if (TestCaseDataUtils.isCheck(tablename,dataindex)) {
                        check.checkString(data.getResultDataBy_one(jsonresult, "Message"), "订单创建成功");
                    } else {
                        report.log(data.getResultDataBy_one(jsonresult, "Message"));
                    }
                }
            }
        }
    }
}
