package com.CCJoy.InterfaceTest.Interface_Design;

import com.CCJoy.InterfaceTest.BaseFrame.*;
import com.CCJoy.InterfaceTest.BaseFrame.CheckPointUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * TODO: 获取商户门店数量并验证门店名称
 *
 * @Author: 邱卫武
 * @Date：2015/9/23
 */
public class Get_shopList_one {
    RequestUtils request = new RequestUtils();
    DataUtils data = new DataUtils();
    CheckPointUtils check = new CheckPointUtils();
    SQLserverUtils sqldata = new SQLserverUtils();
    ReportUtils report = new ReportUtils();

    public Get_shopList_one() {
        // TODO Auto-generated constructor stub
    }

    public void Get_shopList_one(String url_login, String url_get, String tablename, String tablename_login, int dataIndex_login, int dataIndex_get) {
        JSONObject shopName_expected = sqldata.getData_expected(tablename, dataIndex_get);

        // 先取登录的userid跟password
        List<String> result = null;
        try {
            result = request.login_auth(url_login, tablename_login, dataIndex_login);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //获取商户门店列表并并转化为json
        JSONObject orgShopList = JSONObject.fromString(request.doGet_auth(url_get, result));

        //检查商户门店数量是否正确
        JSONArray shopNumArr = JSONArray.fromString(data.getResultDataBy_one(orgShopList, "Data"));
        int shopNum = data.strNum2int(sqldata.getData_oneValue(tablename, dataIndex_get, "shopNum"));
        check.checkIntNum(shopNumArr.length(), shopNum);

        //判断门店数量跟预期结果数量是否一致
        if (shopName_expected.length() == shopNumArr.length()) {
            //检查门店名称是否正确
            for (int i = 0; i < shopNumArr.length(); i++) {
                String display_name = data.getResultDataBy_one(shopNumArr.getJSONObject(i), "display_name");
                check.checkString( display_name,shopName_expected.getString("shopName_expected" + (i + 1)));
            }
        }else{
                report.error("门店数量与预期结果不一致");
        }
    }
}
