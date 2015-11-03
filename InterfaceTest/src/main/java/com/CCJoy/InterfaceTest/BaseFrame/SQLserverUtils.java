package com.CCJoy.InterfaceTest.BaseFrame;

import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import net.sf.json.JSONObject;
import net.sf.json.JSONArray;


public class SQLserverUtils {
    ReportUtils report = new ReportUtils();
    static String dbURL = "jdbc:sqlserver://" + ConfigUtils.DBURL + ";databasename=" + ConfigUtils.DATABASENAME;
    // SQL Driver
    static String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    // 用户名密码
    static String userName = ConfigUtils.DATABASEUSERNAME;
    static String userPwd = ConfigUtils.DATABASEPASSWORD;


    public SQLserverUtils() {
        // TODO Auto-generated constructor stub
    }

    /**
     * TODO：创建数据库连接
     *
     * @return
     */
    private Connection getConnectionSqlServer() {
        Connection dbConn = null;
        try {
            Class.forName(driverName).newInstance();
        } catch (Exception ex) {
            report.error("驱动加载失败");
            ex.printStackTrace();
        }
        try {
            dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dbConn;
    }

    /**
     * TODO：关闭数据库连接
     *
     * @param dbConn
     */
    private void closeConnection(Connection dbConn) {
        try {
            dbConn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            report.error("数据库连接关闭异常");
            e.printStackTrace();
        }
    }

    /**
     * TODO：获取数据库中某一个值
     *
     * @param tableName 表名
     * @param id        第几行数据
     * @param needName  需要查询的列名
     * @return
     */
    public String getData_oneValue(String tableName, int id, String needName) {
        Statement statement = null;
        ResultSet result = null;
        String value = null;
        Connection dbConn = getConnectionSqlServer();
        //创建结果集
        try {
            statement = dbConn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //若结果集非空，则执行SQL语句
        if (statement != null) {
            try {
                result = statement.executeQuery(" select * from " + tableName + " where DataIndex = " + id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            report.log("结果集为空");
            return null;
        }
        //获取所需要的值
        if (result != null) {
            try {
                while (result.next()) {
                    value = result.getString(needName);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            report.log("查询数据为空");
            return null;
        }
        report.log("获取表" + tableName + "【" + needName + "】为【" + value + "】");
        closeConnection(dbConn);
        return value;
    }

    /**
     * TODO：根据需要的列表获取数据库中对应的值
     *
     * @param tableName 表名
     * @param id        第几行数据
     * @param needList  需要的列表
     * @return
     */
    public JSONObject getData_list(String tableName, int id, String[] needList) {
        Connection dbConn = getConnectionSqlServer();
        Statement statement = null;
        ResultSet result = null;
        Map<String, String> params = new HashMap<String, String>();

        //创建结果集
        try {
            statement = dbConn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //若结果集非空，则执行SQL语句
        if (statement != null) {
            try {
                result = statement.executeQuery(" select * from " + tableName + " where DataIndex = " + id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            report.log("结果集为空");
            return null;
        }
        //获取所需要的值
        if (result != null) {
            try {
                while (result.next()) {
                    //循环所有列
                    for (int i = 1; i < result.getMetaData().getColumnCount(); i++) {
                        //循环需要获取的列名
                        for (int j = 0; j < needList.length; j++) {
                            //获取需要获取列名的值
                            if (needList[j].equals(result.getMetaData().getColumnName(i))) {
                                params.put(result.getMetaData().getColumnName(i), result.getString(needList[j]));
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            report.log("查询数据为空");
            return null;
        }
        JSONObject jsonObject = JSONObject.fromObject(params);
        closeConnection(dbConn);
        return jsonObject;
    }

    /**
     * TODO：获取数据库中登录使用的值
     *
     * @param tableName 表名
     * @param id        第几行数据
     * @return
     */
    public JSONObject getData_login(String tableName, int id) {
        Connection dbConn = getConnectionSqlServer();
        Statement statement = null;
        ResultSet result = null;
        Map<String, Object> params = new HashMap<String, Object>();

        //创建结果集
        try {
            statement = dbConn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //若结果集非空，则执行SQL语句
        if (statement != null) {
            try {
                result = statement.executeQuery(" select * from " + tableName + " where DataIndex = " + id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            report.log("结果集为空");
            return null;
        }
        if (result != null) {
            try {
                while (result.next()) {
                    for (int i = 4; i <= result.getMetaData().getColumnCount(); i++) {
                        params.put(result.getMetaData().getColumnName(i), result.getObject(result.getMetaData().getColumnName(i)));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            report.log("查询数据为空");
            return null;
        }
        closeConnection(dbConn);
        report.log(params.toString());
        JSONObject jsonObject = JSONObject.fromObject(params);
        return jsonObject;
    }

    /**
     * TODO 获取用例集中【ISRUN】为true的用例
     *
     * @param tableName
     * @return
     */
    public ResultSet getData_isRun(String tableName) {
        Statement statement = null;
        ResultSet result = null;
        Connection dbConn = getConnectionSqlServer();
        //创建结果集
        try {
            statement = dbConn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //若结果集非空，则执行SQL语句
        if (statement != null) {
            try {
                result = statement.executeQuery("select * from (select ROW_NUMBER()over (order by IsRun) as rowNum, * from " + tableName + "where IsRun = '1')newtable");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            report.log("结果集为空");
            return null;
        }
        //获取所需要的值
        if (result != null) {
            //report.log("获取到查询数据");
            return result;
        } else {
            report.log("查询数据为空");
            return null;
        }
    }

    /**
     * TODO  获取数据库中接口请求所需的参数
     *
     * @param tableName 表名
     * @param id        第几行数据
     * @return
     */
    public JSONObject getData_pamas(String tableName, int id) {
        Connection dbConn = getConnectionSqlServer();
        Statement statement = null;
        ResultSet result = null;
        Map<String, Object> params = new HashMap<String, Object>();
        //创建结果集
        try {
            statement = dbConn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //若结果集非空，则执行SQL语句
        if (statement != null) {
            try {
                result = statement.executeQuery(" select * from " + tableName + " where DataIndex = " + id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            report.log("结果集为空");
            return null;
        }
        if (result != null) {
            try {
                while (result.next()) {
                    //获取从第4列开始，不包含预期结果的列
                    for (int i = 4; i <= result.getMetaData().getColumnCount(); i++) {
                        if (!result.getMetaData().getColumnName(i).contains("_expected")) {
                            //判断获取的value是否为JSONArray，是则转化为JSONArray
                            if (result.getString(result.getMetaData().getColumnName(i)).endsWith("]")) {
                                JSONArray jsonArray = JSONArray.fromString(result.getString(result.getMetaData().getColumnName(i).replaceAll("\r\n", "")));
                                params.put(result.getMetaData().getColumnName(i), jsonArray);
                            }
//                            if (result.getMetaData().getColumnName(i).equals("Goods") || result.getMetaData().getColumnName(i).equals("businesslocations")){
//                                JSONArray jsonArray = JSONArray.fromString(result.getString(result.getMetaData().getColumnName(i)));
//                                params.put(result.getMetaData().getColumnName(i),jsonArray);
//                            }
                            else {
                                params.put(result.getMetaData().getColumnName(i), result.getObject(result.getMetaData().getColumnName(i)));
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            report.log("查询数据为空");
            return null;
        }
        JSONObject jsonObject = JSONObject.fromObject(params);
        closeConnection(dbConn);
        //report.log(params.toString());
        return jsonObject;
    }

    /**
     * TODO：将map数据转化成list<NameValuePair>
     *
     * @param params map数据
     * @return
     */
    public List<NameValuePair> map2list(Map<String, Object> params) {
        // 创建参数队列
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        // 遍历Map并取值放入参数队列
        Set<Map.Entry<String, Object>> entryset = params.entrySet();
        for (Map.Entry<String, Object> entry : entryset) {
            String datakey = entry.getKey();
            Object datavalue = entry.getValue();
            formparams.add(new BasicNameValuePair(datakey, datavalue.toString()));
        }
        //report.log(formparams.toString());
        return formparams;
    }

    /**
     * TODO: 获取预期结果
     *
     * @param tableName 表名
     * @param id        第几行数据
     * @return
     */
    public JSONObject getData_expected(String tableName, int id) {
        Connection dbConn = getConnectionSqlServer();
        Statement statement = null;
        ResultSet result = null;
        Map<String, Object> params = new HashMap<String, Object>();
        //创建结果集
        try {
            statement = dbConn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //若结果集非空，则执行SQL语句
        if (statement != null) {
            try {
                result = statement.executeQuery(" select * from " + tableName + " where DataIndex = " + id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            report.log("结果集为空");
            return null;
        }
        if (result != null) {
            try {
                while (result.next()) {
                    //获取从第4列开始，不包含预期结果的列
                    for (int i = 1; i <= result.getMetaData().getColumnCount(); i++) {
                        if (result.getMetaData().getColumnName(i).contains("_expected")) {
                            //判断获取的value是否为JSONArray，是则转化为JSONArray
                            if (result.getString(result.getMetaData().getColumnName(i)).endsWith("]")) {
                                JSONArray jsonArray = JSONArray.fromString(result.getString(result.getMetaData().getColumnName(i).replaceAll("\r\n", "")));
                                params.put(result.getMetaData().getColumnName(i), jsonArray);
                            } else {
                                params.put(result.getMetaData().getColumnName(i), result.getObject(result.getMetaData().getColumnName(i)));
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            report.log("查询数据为空");
            return null;
        }
        JSONObject jsonObject = JSONObject.fromObject(params);
        closeConnection(dbConn);
        //report.log(params.toString());
        return jsonObject;
    }

    public static void main(String arg[]) {
        //String[] list = {"inputer_name", "last_change_id", "material_id"};
        SQLserverUtils sqltest = new SQLserverUtils();
        sqltest.getData_pamas("[autotest].[dbo].[Get_shopList]", 1);
    }
}
