package com.CCJoy.InterfaceTest.Interface_Design;

import com.CCJoy.InterfaceTest.BaseFrame.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * TODO:
 *
 * @Author: 邱卫武
 * @Date：2015/10/26
 */
public class Get_RequestMaterialsDetail {

    public Get_RequestMaterialsDetail() {
    }

    RequestUtils request = new RequestUtils();
    CheckPointUtils check = new CheckPointUtils();
    SQLserverUtils sqldata = new SQLserverUtils();
    ReportUtils report = new ReportUtils();

    public void doget_requestMaterialsDetail(String url_login, String tablename_login, int dataIndex_login, String url_get, String tablename_get, int dataIndex_get) {
        JSONObject jsonExpected = sqldata.getData_expected(tablename_get, dataIndex_get);
        // 先取登录的userid跟password
        List<String> logindata = null;
        if (TestCaseDataUtils.isRun(tablename_login, dataIndex_login)) {
            try {
                logindata = request.login_auth(url_login, tablename_login, dataIndex_login);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //判断用例是否执行
            if (TestCaseDataUtils.isRun(tablename_get, dataIndex_get)) {
                String requestMaterialGuid = sqldata.getData_oneValue(tablename_get, dataIndex_get, "order_id");
                JSONObject jsonDetail = JSONObject.fromString(request.doGet_auth(url_get + requestMaterialGuid, logindata));
                if (requestMaterialGuid.equals("")) {
                    report.error("请求无效 Bad Request");
                    return;
                }
                JSONArray jsonArray = jsonDetail.getJSONArray("Data");
                //判断是否校验检查点
                if (TestCaseDataUtils.isCheck(tablename_get, dataIndex_get)) {
                    check.checkString(Integer.toString(jsonArray.length()), sqldata.getData_oneValue(tablename_get, dataIndex_get, "GoodsTotal"),"检查请货商品详情数目是否一致");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        check.checkString(jsonArray.getJSONObject(i).getString("goods_name"), jsonExpected.getString("goods_name_expected"+i),"检查商品名称是否一致");
                        check.checkIntNum(jsonArray.getJSONObject(i).getInt("counts"), jsonExpected.getInt("counts_expected"+i),"检查商品数量是否一致");
                        check.checkString(jsonArray.getJSONObject(i).getString("purchase_price"), jsonExpected.getString("purchase_price_expected"+i),"检查请货商品价格是否一致");
                        check.checkString(jsonArray.getJSONObject(i).getString("total"), jsonExpected.getString("total_expected"+i),"检查商品总价是否一致");
                    }
                } else {
                    report.log("获取的请货详情为" + jsonArray.toString());
                }
            }
        }
    }
}
