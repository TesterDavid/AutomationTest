package com.CCJoy.InterfaceTest.BaseFrame;

import java.util.Date;

import bsh.This;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.testng.Reporter;

public class ReportUtils {

    private static Logger logger = Logger.getLogger(RequestUtils.class.getName());

    public ReportUtils() {
        System.setProperty("org.uncommons.reportng.escape-output", "false");
    }

    /*********************************************************************************************
     * 写log和报告操作
     *********************************************************************************************/
    /**
     *
     * @TODO：写日志和报告
     *
     * @author 作者 邱卫武:
     * @date 创建时间：2015年9月5日 下午7:27:35
     * @parameter @param comm
     * @return
     */
    public void log(String... comm) {
        String time = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        if (comm.length == 0) {
            Reporter.log("[" + time + "] <br />");
            logger.info("");
        } else {
            Reporter.log("[" + time + "] " + comm[0] + "<br />");
            logger.info(comm[0]);
        }
    }

    /**
     *
     * @TODO：写日志和报告【debug】
     *
     * 					@author 作者 邱卫武:
     * @date 创建时间：2015年9月5日 下午7:26:45
     * @parameter @param comm
     * @return
     */
    public void debug(String... comm) {
        log("[debug]" + comm[0]);
    }

    /**
     *
     * @TODO：写错误日志和报告
     *
     * @author 作者 邱卫武:
     * @date 创建时间：2015年9月5日 下午7:27:10
     * @parameter @param comm
     * @return
     */
    public void error(String comm) {
        String time = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        Reporter.log("<span style=\"color:#FF0000\"><b>[" + time + "] [ERROR] " + comm + "</b></span><br />");
        logger.error(comm);
    }

    /**
     *
     * @TODO：写警告日志和报告
     *
     * @author 作者 邱卫武:
     * @date 创建时间：2015年9月5日 下午7:27:54
     * @parameter @param comm
     * @return
     */
    public void warn(String comm) {
        String time = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        Reporter.log("<span style=\"color:#FF7F27\"><b>[" + time + "] [WARNING] " + comm + "</b></span><br />");
        logger.warn(comm);
    }

    /**
     *
     * @TODO：写重要的日志和报告
     *
     * @author 作者 邱卫武:
     * @date 创建时间：2015年9月5日 下午7:28:11
     * @parameter @param comm
     * @return
     */
    public void highLight(String comm) {
        log("<span style='background-color:#FFE500;'>" + comm + "</span>");
    }

    /**
     *
     * @TODO： 写绿色高亮的的日志和报告
     *
     * @author 作者 邱卫武:
     * @date 创建时间：2015年9月5日 下午7:28:29
     * @parameter @param comm
     * @return
     */
    public void greenLight(String comm) {
        log("<span style='background-color:#CFFFBA;'>" + comm + "</span>");
    }

    /**
     *
     * @TODO：写醒目的标题
     *
     * @author 作者 邱卫武:
     * @date 创建时间：2015年9月5日 下午7:28:41
     * @parameter @param comm
     * @return
     */
    public void title(String comm) {
        String str;
        str = "<p style=\"color:#0068BD;margin-top:25px;margin-bottom:8px\"><b>";
        str = str
                + "**********************************************************************************************<br>";
        str = str + "* " + comm + "<br>";
        str = str
                + "**********************************************************************************************</b>";
        str = str + "</p>";
        Reporter.log(str);
    }

}

