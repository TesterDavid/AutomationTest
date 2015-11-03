package com.CCJoy.InterfaceTest.Interface_Design;

import com.CCJoy.InterfaceTest.BaseFrame.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @TODO:
 * @Author: 邱卫武
 * @Date：2015/10/16
 */
public class Get_shopList_more {
    RequestUtils request = new RequestUtils();
    DataUtils data = new DataUtils();
    CheckPointUtils check = new CheckPointUtils();
    ReportUtils report = new ReportUtils();
    SQLserverUtils sqldata = new SQLserverUtils();

    public void doGet_shopList_more(String url_login, String url_get, String tablename, String tablename_login) {

        List<String> result = null;
        //获取【IsRun】为1的用例
        ResultSet isRun_case = sqldata.getData_isRun(tablename);

        try {
            for (int i =1; i <= isRun_case.getMetaData().getColumnCount();i++){
                JSONObject shopName_expected = sqldata.getData_expected(tablename, i);
                // 先取登录的userid跟password
                try {
                    result = request.login_auth(url_login, tablename_login, i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //获取返回信息
                JSONObject orgShopList = JSONObject.fromString(request.doGet_auth(url_get, result));
                //获取门店列表
                JSONArray shopNumArr = JSONArray.fromString(data.getResultDataBy_one(orgShopList, "Data"));
                int shopNum = data.strNum2int(sqldata.getData_oneValue(tablename, i, "shopNum"));
                if (TestCaseDataUtils.isCheck(tablename,i)) {
                    //检查商户门店数量是否正确
                    check.checkIntNum(shopNumArr.length(), shopNum);
                    //判断门店数量跟预期结果数量是否一致
                    if (shopName_expected.length() == shopNumArr.length()) {
                        //检查门店名称是否正确
                        for (int j = 0; j < shopNumArr.length(); j++) {
                            String display_name = data.getResultDataBy_one(shopNumArr.getJSONObject(j), "display_name");
                            check.checkString(display_name, shopName_expected.getString("shopName_expected" + (j + 1)));
                        }
                    } else {
                        report.error("门店数量与预期结果不一致");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
