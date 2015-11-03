package com.CCJoy.InterfaceTest.Interface_Design.nouse;

import java.util.List;

import com.CCJoy.InterfaceTest.BaseFrame.*;
import com.CCJoy.InterfaceTest.BaseFrame.CheckPointUtils;
import net.sf.json.JSONObject;
public class Get_config_code {
    RequestUtils request = new RequestUtils();
    DataUtils data = new DataUtils();
    ExcelUtils readexcel = new ExcelUtils();
    CheckPointUtils check = new CheckPointUtils();
    ReportUtils report = new ReportUtils();

    public Get_config_code() {
        // TODO Auto-generated constructor stub
    }

    /**
     *
     * TODO：获取单个配置项的值
     *
     * @author 作者：邱卫武
     * @date 创建时间：2015年9月18日
     * @param url1
     *            登录url
     * @param url2
     *            GET_config_code url
     * @param excelPath
     *            文件地址
     * @param sheetName
     *            sheet名称
     * @param dataNum
     *            第几行数据
     * @throws Exception
     *             异常 void
     */
    public void doget_config_code(String url1, String url2, String excelPath, String tablename,String sheetName, int dataNum)
            throws Exception {
        String  stringResult;
        String config_code;
        String ExpectedResult;
        // 先取登录的userid跟password
        List<String> result = request.login_auth(url1, tablename, dataNum);
        // excel中取config_code的值
        config_code = readexcel.readExcel13_oneValue(excelPath, sheetName, dataNum, "config_code");
        // excel中取预期结果的值
        ExpectedResult = readexcel.readExcel13_oneValue(excelPath, sheetName, dataNum, "预期结果");
        // 执行get操作，取config_code的值
        if (result != null) {
            stringResult = request.doGet_auth(url2 + config_code, result);
            JSONObject jsonResult =JSONObject.fromString(stringResult);
            System.out.println(stringResult);
            if (data.getResultDataBy_2(jsonResult,"Data","user_value").equals(data.getResultDataBy_2(jsonResult,"Data","default_value"))) {
                report.log("用户没有设置配置项" + config_code + "，取默认值");
                check.checkString(data.getResultDataBy_2(jsonResult,"Data","user_value"),ExpectedResult);
            } else {
                report.log("用户自定义配置项" + config_code);
                check.checkString(data.getResultDataBy_2(jsonResult,"Data","user_value"), ExpectedResult);
            }
        } else {
            report.error("用户未登录");
        }
    }
}

