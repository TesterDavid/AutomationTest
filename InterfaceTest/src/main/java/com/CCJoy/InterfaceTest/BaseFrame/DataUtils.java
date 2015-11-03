package com.CCJoy.InterfaceTest.BaseFrame;

import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.lang3.math.NumberUtils;
import net.sf.json.JSONObject;

public class DataUtils {
    private static ReportUtils report = new ReportUtils();

    /**
     * TODO：解析JSON数据，获取需要的key的value（一层JSON数据）
     *
     * @param jsonobject 请求返回的JSON数据
     * @param needData   需要解析的key
     * @return
     * @author 作者：邱卫武
     * @date 创建时间：2015年9月6日
     */
    public String getResultDataBy_one(JSONObject jsonobject, String needData) {
        // 创建Map，接收JSON对象
        HashMap<String, String> dataMap = new HashMap<String, String>();
        // 遍历jsondata对象，并存入Map
        Iterator<String> jsondata = jsonobject.keys();
        while (jsondata.hasNext()) {
            String key = String.valueOf(jsondata.next());
            String value = jsonobject.get(key).toString();
            dataMap.put(key, value);
        }
        String resultData = dataMap.get(needData);
        report.highLight("获取【" + needData + "】的数据为【" + resultData+"】");
        return resultData;
    }

    /**
     * TODO：解析JSON数据，获取需要的key的value（二层JSON数据）
     *
     * @param jsonobject  请求返回的JSON数据
     * @param oneNeedData 需要解析的key（第一层）
     * @param twoNeedData 需要解析的key（第二层）
     * @return
     * @author 作者：邱卫武
     * @date 创建时间：2015年9月6日
     */
    public String getResultDataBy_2(JSONObject jsonobject, String oneNeedData, String twoNeedData) {
        // 创建Map，中转JSON对象
        HashMap<String, String> dataMap = new HashMap<String, String>();
        // 在请求返回后的JSON数据中，获取第二层JSON数据
        JSONObject jsondata = jsonobject.getJSONObject(oneNeedData);
        //打印第二层json数据
        report.highLight(jsondata.toString());
        // 遍历jsondata对象，并存入Map
        Iterator<String> it = jsondata.keys();
        while (it.hasNext()) {
            String key = String.valueOf(it.next());
            String value = jsondata.get(key).toString();
            dataMap.put(key, value);
        }
        // 获取需要的返回数据
        String resultData = dataMap.get(twoNeedData);

        //打印需要的数据
        report.highLight("获取" + oneNeedData + "中的" + twoNeedData + "数据为：" + resultData);
        return resultData;
    }

    /**
     * TODO：String类型数字转换成Double类型数字
     *
     * @author 作者 邱卫武:
     * @date 创建时间：2015年9月5日 下午11:34:14
     * @parameter @param str
     * @parameter @return
     */
    public static double strNum2Double(String str) {
        if (!DataUtils.isNumber(str)) {
            report.warn("[" + str + "]不是数字，返回0");
            return 0;
        }
        return NumberUtils.createDouble(str);
    }

    /**
     * TODO：判断字符串是否为数字
     *
     * @author 作者 邱卫武:
     * @date 创建时间：2015年9月5日 下午11:34:29
     * @parameter @param str
     * @parameter @return
     */
    public static boolean isNumber(String str) {
        return NumberUtils.isNumber(str);
    }

    /**
     * TODO：String类型数字转换成Int类型数字
     *
     * @author 作者 邱卫武:
     * @date 创建时间：2015年9月5日 下午11:34:43
     * @parameter @param str
     * @parameter @return
     */
    public static Integer strNum2int(String str) {
        if (str == null || str.equals("")) {
            return -1;
        }
        return NumberUtils.createInteger(str);
    }

    /**
     * TODO：String字符串转换为double
     *
     * @author 作者 邱卫武:
     * @date 创建时间：2015年9月5日 下午11:35:03
     * @parameter @param str
     * @parameter @return
     */
    public static double stringDouble(String str) {
        double msg = 0;
        if (str != null && !str.equals("") && !str.equals("null")) try {
            msg = Double.parseDouble(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        else {
            report.log("字符串无法转换！");
        }
        return msg;
    }

    /**
     * TODO：double类型转换成String
     *
     * @author 作者 邱卫武:
     * @date 创建时间：2015年9月5日 下午11:35:29
     * @parameter @param flo
     * @parameter @return
     */
    public static String doubleStr(double flo) {
        if (flo == 0) {
            return "";
        } else {
            return String.valueOf(flo);
        }
    }
}
