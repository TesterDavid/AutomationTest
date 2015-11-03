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
 * TODO: 循环执行create order用例
 *
 * @Author: 邱卫武
 * @Date：2015/10/15
 */
public class Post_Create_order_more {
    RequestUtils request = new RequestUtils();
    DataUtils data = new DataUtils();
    CheckPointUtils check = new CheckPointUtils();
    ReportUtils report = new ReportUtils();
    SQLserverUtils sqldata = new SQLserverUtils();

    public Post_Create_order_more() {
        // TODO Auto-generated constructor stub
    }

    public void doCreate_order_more(String url1, String url2, String tablename, String tablename_login, int dataindex_login) throws Exception {
        //创建map，储存从数据库中读取的数据
        Map<String, Object> params = new HashMap<String, Object>();
        List<String> logindata = null;
        //获取【IsRun】为1的用例
        ResultSet result = sqldata.getData_isRun(tablename);
        if (TestCaseDataUtils.isRun(tablename_login, dataindex_login)) {
            logindata = request.login_auth(url1, tablename_login, dataindex_login);
            try {
                //遍历需要run的用例
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
                    report.log("执行第【" + result.getRow() + "】条用例");
                    //执行用例
                    JSONObject stringResponse = JSONObject.fromString(request.doPost_auth(url2, logindata, JSONObject.fromMap(params)));
                    if (sqldata.getData_oneValue(tablename, result.getRow(), "IsCheck").equals("1")) {
                        check.checkString(data.getResultDataBy_one(stringResponse, "Message"), "订单创建成功");
                    } else {
                        report.log(data.getResultDataBy_one(stringResponse, "Message"));
                    }

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}