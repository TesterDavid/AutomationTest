package com.CCJoy.InterfaceTest.Interface_Design.nouse;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.CCJoy.InterfaceTest.BaseFrame.CheckPointUtils;
import com.CCJoy.InterfaceTest.BaseFrame.DataUtils;
import com.CCJoy.InterfaceTest.BaseFrame.ExcelUtils;
import com.CCJoy.InterfaceTest.BaseFrame.RequestUtils;

public class Get_config_groups {

    RequestUtils request = new RequestUtils();
    ExcelUtils readexcel = new ExcelUtils();
    DataUtils data = new DataUtils();
    CheckPointUtils check = new CheckPointUtils();

    public Get_config_groups() {
        // TODO Auto-generated constructor stub
    }

    public void doget_config_groups(String url1, String url2, String excelPath,String tablename, String sheetName, int dataNum)
            throws Exception {
        String stringResult;
        JSONObject jsonResult;
        // 先取登录的userid跟password
        List<String> result = request.login_auth(url1, tablename, dataNum);
        String Expected_group_id;
        String Expected_group_name;
        String Expected_is_enabled;
        if (result != null) {
            stringResult = request.doGet_auth(url2, result);
            System.out.println(stringResult);
            jsonResult = new JSONObject(stringResult);
            JSONArray jsonArr = new JSONArray(jsonResult.get("Data").toString());
            check.checkIntNum(jsonArr.length(),
                    data.strNum2int(readexcel.readExcel13_oneValue(excelPath, sheetName, dataNum, "群组数")));
            for (int i = 0; i < jsonArr.length(); i++) {
                // excel中取预期结果的值
                Expected_group_id = readexcel.readExcel13_oneValue(excelPath, sheetName, dataNum,
                        "group_id【" + i + "】预期结果");
                Expected_group_name = readexcel.readExcel13_oneValue(excelPath, sheetName, dataNum,
                        "group_name【" + i + "】预期结果");
                Expected_is_enabled = readexcel.readExcel13_oneValue(excelPath, sheetName, dataNum,
                        "is_enabled【" + i + "】预期结果");
                // 将JSON数组中的元素放入JSON对象
                jsonResult = jsonArr.getJSONObject(i);
                check.checkString(jsonResult.get("group_id").toString(), Expected_group_id);
                check.checkString(jsonResult.get("group_name").toString(), Expected_group_name);
                check.checkString(jsonResult.get("is_enabled").toString(), Expected_is_enabled);
            }
        }
    }
}

