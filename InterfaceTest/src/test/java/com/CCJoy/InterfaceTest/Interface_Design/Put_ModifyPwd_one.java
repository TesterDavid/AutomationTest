package com.CCJoy.InterfaceTest.Interface_Design;

import com.CCJoy.InterfaceTest.BaseFrame.*;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * TODO: 修改密码测试类
 *
 * @Author: 邱卫武
 * @Date：2015/10/21
 */
public class Put_ModifyPwd_one {
    RequestUtils request = new RequestUtils();
    SQLserverUtils sqldata = new SQLserverUtils();
    CheckPointUtils check = new CheckPointUtils();
    DataUtils data = new DataUtils();
    ReportUtils report = new ReportUtils();

    public void doput_ModifyPwd_one(String url_login, String tablename_login, String url_put, String tablename_put, int dataIndex_login, int dataIndex_put) {
        String stringResult;
        List<String> result = null;
        JSONObject jsonParams = sqldata.getData_pamas(tablename_put, dataIndex_put);
        if (TestCaseDataUtils.isRun(tablename_login, dataIndex_login)){
            try {
                //获取登录userid跟token
                result = request.login_auth(url_login, tablename_login, dataIndex_login);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (result != null) {
                //判断用例是否运行
                if (TestCaseDataUtils.isRun(tablename_put,dataIndex_put)){
                    //执行用例
                    stringResult = request.doPut_auth(url_put,result,jsonParams);
                    JSONObject jsonresult = JSONObject.fromString(stringResult);
                    //获取message的内容
                    String message = jsonresult.getString("Message");
                    //判断用例是否校验检查点
                    if (TestCaseDataUtils.isCheck(tablename_put,dataIndex_put)){
                        check.checkString(message,sqldata.getData_oneValue(tablename_put,dataIndex_put,"message_expected"));
                    }else{
                        report.log(message);
                    }
                }
            }
        }
    }
}
