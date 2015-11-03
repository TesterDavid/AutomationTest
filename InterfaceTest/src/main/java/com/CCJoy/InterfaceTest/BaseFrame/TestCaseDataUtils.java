package com.CCJoy.InterfaceTest.BaseFrame;



/**
 * TODO: 判断数据库中数据
 *
 * @Author: 邱卫武
 * @Date：2015/10/10
 */
public class TestCaseDataUtils {
    protected static ReportUtils report = new ReportUtils();
    protected static SQLserverUtils sqldata = new SQLserverUtils();

    /**
     * TODO： 判断数据库中【ISRUN】列的值，1为run，0为no
     *
     * @param tablename 表名
     * @param dataindex 第几行数据
     * @return
     */
    public static boolean isRun(String tablename, int dataindex) {
        if (sqldata.getData_oneValue(tablename, dataindex, "IsRun").equals("1")) {
            return true;
        } else {
            report.highLight("数据库【" + tablename + "】表中第[" + dataindex + "]行的【IsRun】列为false，该用例不执行");
            return false;
        }
    }

    /**
     * TODO： 判断数据库中【ISCHECK】列的值，1为检查，0为不检查
     *
     * @param tablename 表名
     * @param dataindex 第几行数据
     * @return
     */
    public static boolean isCheck(String tablename, int dataindex) {
        if (sqldata.getData_oneValue(tablename, dataindex, "IsCheck").contains("1")) {
            return true;
        } else {
            report.highLight("数据库【" + tablename + "】表中第[" + dataindex + "]行的【IsCheck】列为false，该用例不校验检查点");
            return false;
        }
    }

    /**
     * TODO：判断字段非空
     *
     * @param tablename 表名
     * @param dataindex 第几行数据
     * @param titleName 列名
     * @return
     */
    public static boolean isNull(String tablename, int dataindex, String titleName) {
        String titleValue = sqldata.getData_oneValue(tablename, dataindex, titleName);
        if (titleValue == null) {
            return true;
        } else if (titleValue.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * TODO：判断字段非不空
     *
     * @param tablename 表名
     * @param dataindex 第几行数据
     * @param titleName 列名
     * @return
     */
    public static boolean isNotNull(String tablename, int dataindex, String titleName) {
        String titleValue = sqldata.getData_oneValue(tablename, dataindex, titleName);
        if (titleValue == null) {
            return false;
        } else if (titleValue.equals("")) {
            return false;
        } else {
            return true;
        }
    }
}
