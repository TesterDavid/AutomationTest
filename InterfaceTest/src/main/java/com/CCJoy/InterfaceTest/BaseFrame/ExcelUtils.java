package com.CCJoy.InterfaceTest.BaseFrame;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
    ReportUtils report = new ReportUtils();
    private XSSFWorkbook book13 = null;
    private HSSFWorkbook book03 = null;
    private XSSFSheet sheet13 = null;
    private HSSFSheet sheet03 = null;
    private int rowNum = 0;
    private String[] columnnName;

    public ExcelUtils() {

    }

    /**
     * TODO：Excel驱动TestNG(读取全部数据)
     */
    public ExcelUtils(String excelpath, String sheetName, int datanum) {
        report.log(("使用自定义的Excel文件：" + excelpath));
        try {
            // 判断Excel版本
            if (excelpath.endsWith(".xlsx")) {
                for (int i = 0; i <= datanum; i++) {
                    readExcel13(excelpath, sheetName, i);
                }
            } else {
                for (int i = 0; i <= datanum; i++) {
                    readExcel03(excelpath, sheetName, i);
                }
            }
        } catch (Exception e) {
            report.error(e.toString());
        }
    }

    /**
     * TODO：Excel驱动TestNG(读取第几条)
     *
     * @param excelpath 文件路径
     * @param sheetName sheet名称
     * @param dataindex 执行用例序号
     * @param commd     备注
     */
    public ExcelUtils(String excelpath, String sheetName, int dataindex, String commd) {
        report.log("使用自定义的Excel文件：" + excelpath);

        try {
            // 判断Excel版本
            if (excelpath.endsWith(".xlsx")) {
                readExcel13(excelpath, sheetName, dataindex);
            } else {
                readExcel03(excelpath, sheetName, dataindex);
            }
        } catch (Exception e) {
            report.error(e.toString());
        }
    }

    /**
     * TODO：读取Excel13中的数据并放入List<NameValuePair>
     *
     * @param excelPath 文件路径
     * @param sheetName sheet名称
     * @param dataindex 数据序号
     * @return List<NameValuePair>
     * @throws Exception
     * @author 作者：邱卫武
     * @date 创建时间：2015年9月8日
     */
    public List<NameValuePair> readExcel13(String excelPath, String sheetName, int dataindex) throws Exception {
        report.log("读取自定义的Excel文件：" + excelPath);
        // 实例化Excel13
        book13 = new XSSFWorkbook(OPCPackage.open(new File(excelPath)));
        // 获取sheetName
        sheet13 = book13.getSheet(sheetName);
        // 获取总行数
        rowNum = sheet13.getPhysicalNumberOfRows();
        // 获取列名行
        Row colNamerow = sheet13.getRow(0);
        // 获取数据行
        Row datarow = sheet13.getRow(dataindex);
        // 创建Map中转数据
        Map<String, String> params = new HashMap<String, String>();

        // 遍历列名行，获取列名与数据的值对，<key，value>形式，并放入Map中
        Iterator<Cell> heads = colNamerow.cellIterator();
        columnnName = new String[colNamerow.getPhysicalNumberOfCells()];
        int count = 0;

        while (heads.hasNext()) {
            Cell cell = heads.next();
            columnnName[count] = cell.getRichStringCellValue().toString();
            String colName = columnnName[count];
            String data = datarow.getCell(count + 1).toString();
            if (colName.equals("app_version") || colName.equals("device_id") || colName.equals("UserName") || colName.equals("Password")) {
                params.put(colName, data);
                count++;
            }
            // else{
            //     continue;
            // }

        }
        // 创建参数队列
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        // 遍历Map并取值放入参数队列
        Set<Map.Entry<String, String>> entryset = params.entrySet();
        for (Map.Entry<String, String> entry : entryset) {
            String datakey = entry.getKey();
            String datavalue = entry.getValue();
            formparams.add(new BasicNameValuePair(datakey, datavalue));

        }
        report.greenLight("获取Excel的数据为：" + formparams);
        return formparams;
    }

    /**
     * @param excelPath 文件地址
     * @param sheetName sheet名称
     * @param names     List<String>
     * @param dataindex 第几行数据
     * @return
     * @throws Exception
     * @TODO 根据List读取Excel13中的对应的数据并放入List<NameValuePair>
     * @author 邱卫武
     */
    public List<NameValuePair> readExcel13_List(String excelPath, String sheetName, String[] names, int dataindex) throws Exception {
        report.log("读取自定义的Excel文件：" + excelPath);
        // 实例化Excel13
        book13 = new XSSFWorkbook(OPCPackage.open(new File(excelPath)));
        // 获取sheetName
        sheet13 = book13.getSheet(sheetName);
        // 获取总行数
        rowNum = sheet13.getPhysicalNumberOfRows();
        // 获取列名行
        Row colNamerow = sheet13.getRow(0);
        // 获取数据行
        Row datarow = sheet13.getRow(dataindex);
        // 创建Map中转数据
        Map<String, String> params = new HashMap<String, String>();

        // 遍历列名行，获取列名与数据的值对，<key，value>形式，并放入Map中
        Iterator<Cell> heads = colNamerow.cellIterator();
        columnnName = new String[colNamerow.getPhysicalNumberOfCells()];
        int count = 0;

        while (heads.hasNext()) {
            Cell cell = heads.next();
            columnnName[count] = cell.getRichStringCellValue().toString();
            String colName = columnnName[count];
            //String data = datarow.getCell(count + 1).toString();
            for (int i = 0; i < names.length; i++)
                if (colName.equals(names[i])) {
                    String data = datarow.getCell(count).toString();
                    if (data.equals("1.0")) {
                        params.put(colName, "1");
                    } else if (data.equals("18.0")) {
                        params.put(colName, "18");
                    } else if (data.equals("21.0")) {
                        params.put(colName, "21");
                    } else {
                        params.put(colName, data);
                    }
                }
            count++;
        }
        // 创建参数队列
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        // 遍历Map并取值放入参数队列
        Set<Map.Entry<String, String>> entryset = params.entrySet();
        for (Map.Entry<String, String> entry : entryset) {
            String datakey = entry.getKey();
            String datavalue = entry.getValue();
            formparams.add(new BasicNameValuePair(datakey, datavalue));

        }
        report.greenLight("获取Excel的数据为：" + formparams);
        return formparams;
    }

    /**
     * TODO：获取Excel中预期结果的值
     *
     * @param excelPath 文件路径
     * @param sheetName sheet名称
     * @param dataindex 数据序号
     * @return
     * @throws Exception String
     * @author 作者：邱卫武
     * @date 创建时间：2015年9月8日
     */
    public String readExcel13_oneValue(String excelPath, String sheetName, int dataindex, String name) throws Exception {
        String ExpectedResult = null;
        // 实例化Excel13
        book13 = new XSSFWorkbook(OPCPackage.open(new File(excelPath)));
        // 获取sheetName
        sheet13 = book13.getSheet(sheetName);
        // 获取总行数
        rowNum = sheet13.getPhysicalNumberOfRows();
        // 获取列名行
        Row colNamerow = sheet13.getRow(0);
        // 获取数据行
        Row datarow = sheet13.getRow(dataindex);

        // 遍历列名行，取预期结果的值
        Iterator<Cell> heads = colNamerow.cellIterator();
        columnnName = new String[colNamerow.getPhysicalNumberOfCells()];
        int count = 0;
        Cell cell;
        while (heads.hasNext()) {
            cell = heads.next();
            columnnName[count] = cell.getRichStringCellValue().toString();
            String colName = columnnName[count];
            if (colName.equals(name)) {
                ExpectedResult = datarow.getCell(count).toString();
                break;
            }
            count++;
        }
        report.highLight("从Excel中读取第" + dataindex + "条用例的" + name + "为：" + ExpectedResult);
        return ExpectedResult;
    }

    /**
     * TODO：读取Excel03中的数据并放入List<NameValuePair>
     *
     * @param excelPath 文件名称
     * @param sheetName sheet名称
     * @param dataindex 数据序号
     * @return
     * @throws Exception List<NameValuePair>
     * @author 作者：邱卫武
     * @date 创建时间：2015年9月8日
     */
    public List<NameValuePair> readExcel03(String excelPath, String sheetName, int dataindex) throws Exception {
        report.log("读取自定义的Excel文件：" + excelPath);
        // 实例化Excel03
        book03 = new HSSFWorkbook(new FileInputStream(new File(excelPath)));
        // 获取sheetName
        sheet03 = book03.getSheet(sheetName);
        // 获取总行数
        rowNum = sheet03.getPhysicalNumberOfRows();
        // 获取列名行
        Row colNamerow = sheet13.getRow(0);
        // 获取数据行
        report.highLight("读取第" + dataindex + "条用例");
        Row datarow = sheet13.getRow(dataindex);
        // 创建Map中转数据
        Map<String, String> params = new HashMap<String, String>();

        // 遍历列名行，获取列名与数据的值对，<key，value>形式，并放入Map中
        Iterator<Cell> heads = colNamerow.cellIterator();
        columnnName = new String[colNamerow.getPhysicalNumberOfCells()];
        int count = 0;
        while (heads.hasNext()) {
            Cell cell = heads.next();
            columnnName[count] = cell.getRichStringCellValue().toString();
            String colName = columnnName[count];
            String data = datarow.getCell(count + 1).toString();
            if (colName.equals("DataIndex") || colName.equals("预期结果")) {
                continue;
            }
            params.put(colName, data);
            count++;
        }
        // 创建参数队列
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        // 遍历Map并取值放入参数队列
        Set<Map.Entry<String, String>> entryset = params.entrySet();
        for (Map.Entry<String, String> entry : entryset) {
            String datakey = entry.getKey();
            String datavalue = entry.getValue();
            formparams.add(new BasicNameValuePair(datakey, datavalue));
        }
        report.greenLight("获取Excel的数据为：" + formparams);
        return formparams;
    }

    /**
     * TODO：获取Excel中预期结果的值
     *
     * @param excelPath 文件名称
     * @param sheetName sheet名称
     * @param dataindex 数据序号
     * @return
     * @throws Exception String
     * @author 作者：邱卫武
     * @date 创建时间：2015年9月8日
     */
    public String readExcel03_ExpectedResult(String excelPath, String sheetName, int dataindex) throws Exception {
        String ExpectedResult = null;
        // 实例化Excel03
        book03 = new HSSFWorkbook(new FileInputStream(new File(excelPath)));
        // 获取sheetName
        sheet03 = book03.getSheet(sheetName);
        // 获取总行数
        rowNum = sheet03.getPhysicalNumberOfRows();
        // 获取列名行
        Row colNamerow = sheet13.getRow(0);

        // 获取数据行
        Row datarow = sheet13.getRow(dataindex);

        // 遍历列名行，取预期结果的值
        Iterator<Cell> heads = colNamerow.cellIterator();
        columnnName = new String[colNamerow.getPhysicalNumberOfCells()];
        int count = 0;
        Cell cell;
        while (heads.hasNext()) {
            cell = heads.next();
            columnnName[count] = cell.getRichStringCellValue().toString();
            String colName = columnnName[count];
            if (colName.equals("预期结果")) {
                ExpectedResult = datarow.getCell(count).toString();
                break;
            }
            count++;
        }
        report.highLight("从Excel中读取第" + dataindex + "条用例的预期结果为：" + ExpectedResult);
        return ExpectedResult;
    }

    /**
     * @return
     * @TODO：判断是否有数据
     * @author 作者 邱卫武:
     * @date 创建时间：2015年9月4日 下午2:26:22
     * @parameter @return
     */
    public boolean hasNext() {
        int curRowNo = 0;
        return !(this.rowNum == 0 || curRowNo >= this.rowNum);
    }

}

