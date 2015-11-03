package com.CCJoy.InterfaceTest.Interface_Design;

import com.CCJoy.InterfaceTest.BaseFrame.*;
import net.sf.json.JSONObject;

/**
 * TODO: 登录接口测试类
 *
 * @Author: 邱卫武
 * @Date：2015/10/12
 */
public class Login_one {

    RequestUtils request = new RequestUtils();
    CheckPointUtils check = new CheckPointUtils();
    ReportUtils report = new ReportUtils();
    SQLserverUtils sqldata = new SQLserverUtils();

    public void login(String url, String tablename, int dataindex) {

        //判断用例是否执行
        if (TestCaseDataUtils.isRun(tablename, dataindex)) {
            JSONObject response = JSONObject.fromString(request.doPost(url, sqldata.getData_login(tablename, dataindex)));
            //判断用例是否需要检查预期结果
            String message = response.getString("Message");
            if (TestCaseDataUtils.isCheck(tablename, dataindex)) {
                //检查登录状态
                if ("true".equals(response.getString("IsSuccess"))) {
                    report.log("登录成功");
                }
                if (message.equals("密码错误或账户已禁用")) {
                    check.checkString(message, "密码错误或账户已禁用");
                }
                if (message.equals("未匹配到该公司")) {
                    check.checkString(message, "未匹配到该公司");
                }
            } else {
                report.log(message);
            }
        } else {
            report.error("数据库【" + tablename + "】表中第[" + dataindex + "]行的【IsRun】列为false，该用例不执行");
        }
    }
}
