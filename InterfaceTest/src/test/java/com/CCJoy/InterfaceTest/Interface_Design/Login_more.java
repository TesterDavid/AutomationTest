package com.CCJoy.InterfaceTest.Interface_Design;

import com.CCJoy.InterfaceTest.BaseFrame.*;
import net.sf.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @TODO:
 * @Author: 邱卫武
 * @Date：2015/10/12
 */
public class Login_more {

    RequestUtils request = new RequestUtils();
    DataUtils data = new DataUtils();
    CheckPointUtils check = new CheckPointUtils();
    ReportUtils report = new ReportUtils();
    SQLserverUtils sqldata = new SQLserverUtils();

    public void login(String url, String tablename) {
        Map<String, Object> params = new HashMap<String, Object>();
        ResultSet result = sqldata.getData_isRun(tablename);
        try {
            //遍历结果集每行数据
            while (result.next()) {
                for (int j = 5; j <= result.getMetaData().getColumnCount(); j++) {
                    if (!result.getMetaData().getColumnName(j).contains("_expected")) {
                        params.put(result.getMetaData().getColumnName(j), result.getObject(result.getMetaData().getColumnName(j)));
                    }
                }
                //执行登录请求
                JSONObject response = JSONObject.fromString(request.doPost(url, JSONObject.fromObject(params)));
                String message = data.getResultDataBy_one(response, "Message");
                if (sqldata.getData_oneValue(tablename, result.getRow(), "IsCheck").equals("1")) {
                    //检查登录状态
                    if ("true".equals(data.getResultDataBy_one(response, "IsSuccess"))) {
                        report.log("登录成功");
                    } else if (message.equals("密码错误或账户已禁用")) {
                        check.checkString(message, "密码错误或账户已禁用");
                    } else if (message.equals("未匹配到该公司")) {
                        check.checkString(message, "未匹配到该公司");
                    }
                } else {
                    report.log("message为" + message);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
