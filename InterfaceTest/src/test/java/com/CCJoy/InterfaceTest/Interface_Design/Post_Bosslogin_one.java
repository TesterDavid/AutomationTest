package com.CCJoy.InterfaceTest.Interface_Design;

import com.CCJoy.InterfaceTest.BaseFrame.*;
import net.sf.json.JSONObject;

/**
 * TODO: 老板端登录接口测试类
 * @Author: 邱卫武
 * @Date：2015/10/19
 */
public class Post_Bosslogin_one {
    RequestUtils request = new RequestUtils();
    SQLserverUtils sqldata = new SQLserverUtils();
    CheckPointUtils check = new CheckPointUtils();
    ReportUtils report = new ReportUtils();

    public Post_Bosslogin_one() {
        // TODO Auto-generated constructor stub
    }

    public void dopost_Bosslogin_one(String url, String tablename, int dataindex) {
        //读取数据库中老板端登陆参数
        JSONObject params = sqldata.getData_pamas(tablename, dataindex);
        //判断用例是否允许
        if (TestCaseDataUtils.isRun(tablename, dataindex)) {
            //执行用例
            String stringResult = request.doPost(url, params);
            JSONObject jsonResult = JSONObject.fromString(stringResult);
            //获取 Message 信息
            String message =jsonResult.getString("Message");
            //判断用例是否校验检查点
            if (TestCaseDataUtils.isCheck(tablename, dataindex)) {
                //检查登录状态
                check.checkString(message, sqldata.getData_oneValue(tablename, dataindex, "Message_expected"));
                if (message.equals("null")) {
                    report.log("登录成功");
                } else {
                    report.log("登录失败");
                }
            }
        }
    }
}


