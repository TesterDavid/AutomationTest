package com.CCJoy.InterfaceTest.Interface_Design.nouse;

import java.util.List;

import com.CCJoy.InterfaceTest.BaseFrame.*;
import org.apache.http.NameValuePair;
import net.sf.json.JSONObject;

public class LoginInterface {
    RequestUtils request = new RequestUtils();
    ExcelUtils readexcel = new ExcelUtils();
    DataUtils data = new DataUtils();
    CheckPointUtils check = new CheckPointUtils();
    ReportUtils report = new ReportUtils();
    SQLserverUtils sqldata = new SQLserverUtils();

    public LoginInterface() {
        // TODO Auto-generated constructor stub
    }

    /**
     *
     * TODO：登录接口测试
     *
     * @author 作者：邱卫武
     * @date 创建时间：2015年9月8日
     * @param url
     * @param excelPath
     * @param sheetName
     * @throws Exception
     *             void
     */
    public void login(String url, String excelPath, String sheetName, String tablename,int datanum) throws Exception {
        for (int i = 1; i <= datanum; i++) {
            JSONObject response =JSONObject.fromString( request.doPost(url, sqldata.getData_login(tablename, i))); // 判断登录是否成功
            if ("false".equals(data.getResultDataBy_one(response, "IsSuccess"))) {
                String message = data.getResultDataBy_one(response, "Message");
                check.checkString(message, readexcel.readExcel13_oneValue(excelPath, sheetName, i,"预期结果"));
            } else {
                report.log("登录成功");
            }
        }
    }

    /**
     *
     * TODO：登录返回usserID跟password
     *
     * @author 作者：邱卫武
     * @date 创建时间：2015年9月8日
     * @param url
     * @param excelPath
     * @param sheetName
     * @return
     * @throws Exception
     *             List<String>
     */
    public List<String> login_auth(String url, String excelPath,String tablename, String sheetName, int dataindex) throws Exception {
        List<NameValuePair> formparams;
        formparams = readexcel.readExcel13(excelPath, sheetName, dataindex);
        return request.doPostBy_login(url, sqldata.getData_login(tablename, dataindex));
    }
}
