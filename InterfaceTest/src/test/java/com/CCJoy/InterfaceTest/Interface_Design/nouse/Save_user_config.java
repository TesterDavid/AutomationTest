package com.CCJoy.InterfaceTest.Interface_Design.nouse;

import com.CCJoy.InterfaceTest.BaseFrame.*;
import com.CCJoy.InterfaceTest.BaseFrame.CheckPointUtils;
import org.apache.http.NameValuePair;
import java.util.List;
import net.sf.json.JSONObject;

/**
 * @TODO:
 * @Author: 邱卫武
 * @Date：2015/9/22
 */
public class Save_user_config {
    RequestUtils request = new RequestUtils();
    DataUtils data = new DataUtils();
    ExcelUtils readexcel = new ExcelUtils();
    CheckPointUtils check = new CheckPointUtils();
    ReportUtils report = new ReportUtils();
    SQLserverUtils sqldata = new SQLserverUtils();
    public Save_user_config() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @TODO 保存用户单个配置项设置
     *
     * @author 邱卫武
     * @param url1  登录url
     * @param url2  保存配置url
     * @param url3  获取配置直url
     * @param excelPath   文件地址
     * @param sheetName   sheet名称
     * @param dataIndex   第几行数据
     * @throws Exception
     */
    public void doSave_user_config(String url1, String url2, String url3,String tablename, String excelPath, String sheetName, int dataIndex) throws Exception {
        String[] names = {"user_config_id", "config_item_id", "org_id", "config_value", "inputer", "inputer_name", "input_time"};
        // 先取登录的userid跟password
        List<String> result = request.login_auth(url1, tablename, dataIndex);
        //从Excel中获取执行POST请求的参数
        List<NameValuePair> forparams = readexcel.readExcel13_List(excelPath, sheetName, names, dataIndex);
        //执行post save_user_config 请求
        //request.doPost_auth(url2, result, sqldata.getData_login(tablename, dataIndex));
        //通过config_code跟config_value 确认保存是否成功
        String config_code = readexcel.readExcel13_oneValue(excelPath, sheetName, dataIndex, "config_code");
        String config_value = readexcel.readExcel13_oneValue(excelPath, sheetName, dataIndex, "config_value");

        JSONObject jsonResult = JSONObject.fromString(request.doGet_auth(url3 + config_code, result));

        String  user_value = data.getResultDataBy_2(jsonResult,"Data","user_value");
        report.log(config_value);
        //检查保存的配置项值跟系统数据库中的值是否一致
        check.checkString(user_value,config_value);
    }

}
