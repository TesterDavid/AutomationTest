package com.CCJoy.InterfaceTest.Interface_Design.nouse;

import java.util.List;

import com.CCJoy.InterfaceTest.BaseFrame.SQLserverUtils;
import org.apache.http.NameValuePair;
import com.CCJoy.InterfaceTest.BaseFrame.ExcelUtils;
import com.CCJoy.InterfaceTest.BaseFrame.ReportUtils;
import com.CCJoy.InterfaceTest.BaseFrame.RequestUtils;

public class LogoutInterface {
    RequestUtils request = new RequestUtils();
    ExcelUtils readexcel = new ExcelUtils();
    ReportUtils report = new ReportUtils();
    SQLserverUtils sqldata = new SQLserverUtils();

    public LogoutInterface() {
        // TODO Auto-generated constructor stub
    }

    /**
     *
     * TODO：注销登陆 basic auth认证 先登录再登出
     *
     * @author 作者：邱卫武
     * @date 创建时间：2015年9月7日
     * @param url1
     *            登录url
     * @param url2
     *            登出url
     * @param excelPath
     *            文件地址
     * @param sheetName
     *            sheet名称
     * @throws Exception
     *             void
     */
    public void logout(String url1, String url2, String excelPath, String sheetName,String tablename,int dataindex) throws Exception {
        List<NameValuePair> formparams;
        formparams = readexcel.readExcel13(excelPath, sheetName,dataindex);
        List<String> testdata = request.doPostBy_login(url1, sqldata.getData_login(tablename, dataindex));
        if (request.doDelete_logout(url2, testdata)) {
            report.log("登出成功");
        } else {
            report.log("登出异常");
        }
    }

    /**
     *
     * TODO：根据用户数据注销登陆
     *
     * @author 作者：邱卫武
     * @date 创建时间：2015年9月8日
     * @param url
     * @param testdata
     * @throws Exception
     *             void
     */
    public void logout(String url, List<String> testdata) throws Exception {
        if (request.doDelete_logout(url, testdata)) {
            report.log("登出成功");
        } else {
            report.log("登出异常");
        }

    }
}
