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
 * @Date：2015/10/21
 */
public class Post_Bosslogin_more {
    RequestUtils request = new RequestUtils();
    SQLserverUtils sqldata = new SQLserverUtils();
    CheckPointUtils check = new CheckPointUtils();
    DataUtils data = new DataUtils();
    ReportUtils report = new ReportUtils();

    public Post_Bosslogin_more() {
        // TODO Auto-generated constructor stub
    }

    public void dopost_Bosslogin_more(String url, String tablename) {
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
                if (TestCaseDataUtils.isCheck(tablename, result.getRow())) {
                    //检查登录状态
                    check.checkString(message, sqldata.getData_oneValue(tablename, result.getRow(), "Message_expected"));
                    if (message.equals("null")) {
                        report.log("登录成功");
                    } else {
                        report.log("登录失败");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
