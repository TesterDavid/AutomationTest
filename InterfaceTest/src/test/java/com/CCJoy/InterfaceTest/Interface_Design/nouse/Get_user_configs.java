package com.CCJoy.InterfaceTest.Interface_Design.nouse;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.CCJoy.InterfaceTest.BaseFrame.CheckPointUtils;
import com.CCJoy.InterfaceTest.BaseFrame.DataUtils;
import com.CCJoy.InterfaceTest.BaseFrame.ExcelUtils;
import com.CCJoy.InterfaceTest.BaseFrame.ReportUtils;
import com.CCJoy.InterfaceTest.BaseFrame.RequestUtils;

public class Get_user_configs {

    RequestUtils request = new RequestUtils();
    ExcelUtils readexcel = new ExcelUtils();
    DataUtils data = new DataUtils();
    CheckPointUtils check = new CheckPointUtils();
    ReportUtils report = new ReportUtils();

    public Get_user_configs() {
        // TODO Auto-generated constructor stub
    }

    public void doget_user_config(String url1, String url2, String excelPath,String tablename, String sheetName, int dataNum)
            throws Exception {
        String stringResult;
        JSONObject jsonResult;
        String groupGuid;
        String ExpectedResult;
        // 先取登录的userid跟password
        List<String> result = request.login_auth(url1, tablename, dataNum);
        // excel中取groupGuid的值
        groupGuid = readexcel.readExcel13_oneValue(excelPath, sheetName, dataNum, "groupGuid");
        if (result != null) {
            stringResult = request.doGet_auth(url2 + groupGuid, result);

            JSONArray jsonArr = new JSONArray(new JSONObject(stringResult).get("Data").toString());
            check.checkIntNum(jsonArr.length(), data.strNum2int(readexcel.readExcel13_oneValue(excelPath, sheetName, dataNum, "配置项数")));
            for (int i = 0; i <jsonArr.length(); i++) {
                // excel中取预期结果的值
                ExpectedResult = readexcel.readExcel13_oneValue(excelPath, sheetName, dataNum, "配置项"+i+"预期结果");
                jsonResult = jsonArr.getJSONObject(i);
                if (jsonResult.get("user_value").equals(jsonResult.get("default_value"))) {
                    report.log("用户没有设置配置项" + jsonResult.get("config_code") + "，取默认值");
                    check.checkString(jsonResult.get("user_value").toString(), ExpectedResult);
                } else {
                    report.log("用户自定义配置项" + jsonResult.get("config_code"));
                    check.checkString(jsonResult.get("user_value").toString(), ExpectedResult);
                }
            }
        } else {
            report.error("用户未登录");
        }
    }
}
