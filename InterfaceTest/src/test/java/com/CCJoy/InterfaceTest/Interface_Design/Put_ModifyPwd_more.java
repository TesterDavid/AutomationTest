package com.CCJoy.InterfaceTest.Interface_Design;

import com.CCJoy.InterfaceTest.BaseFrame.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO: 修改密码测试类
 *
 * @Author: 邱卫武
 * @Date：2015/10/21
 */
public class Put_ModifyPwd_more {
    RequestUtils request = new RequestUtils();
    SQLserverUtils sqldata = new SQLserverUtils();
    CheckPointUtils check = new CheckPointUtils();
    ReportUtils report = new ReportUtils();

    public Put_ModifyPwd_more() {
    }

    public void doput_ModifyPwd_more(String url_login, String tablename_login, int dataIndex_login, String url_put, String tablename_put) {
        Map<String, Object> params = new HashMap<String, Object>();
        ResultSet result = sqldata.getData_isRun(tablename_put);
        List<String> logindata = null;

        if (TestCaseDataUtils.isRun(tablename_login, dataIndex_login)) {
            try {
                logindata = request.login_auth(url_login, tablename_login, dataIndex_login);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                while (result.next()) {
                    //遍历用例的所有参数，储存到map
                    for (int j = 5; j <= result.getMetaData().getColumnCount(); j++) {
                        if (!result.getMetaData().getColumnName(j).contains("_expected")) {
                            if (result.getString(result.getMetaData().getColumnName(j)).endsWith("]")) {
                                JSONArray jsonArray = JSONArray.fromString(result.getString(result.getMetaData().getColumnName(j).replaceAll("\r\n", "")));
                                params.put(result.getMetaData().getColumnName(j), jsonArray);
                            } else {
                                params.put(result.getMetaData().getColumnName(j), result.getObject(result.getMetaData().getColumnName(j)));
                            }
                        }
                    }
                    JSONObject jsonResult = JSONObject.fromString(request.doPut_auth(url_put, logindata, JSONObject.fromObject(params)));
                    //获取message的内容
                    String message = jsonResult.getString("Message");
                    //判断用例是否校验检查点
                    if (TestCaseDataUtils.isCheck(tablename_put, result.getRow())) {
                        check.checkString(message, sqldata.getData_oneValue(tablename_put, result.getRow(), "message_expected"));
                    } else {
                        report.log(message);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
