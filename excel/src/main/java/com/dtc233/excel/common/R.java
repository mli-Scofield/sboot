package com.dtc233.excel.common;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import org.apache.catalina.util.ParameterMap;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.*;

/**
 * @Author 丁廷宠 413778746@qq.com @Describe @Date： 2022/8/12 10:51 Copyright(c)2018-2021 Livolo All
 * rights reserved.
 */
public class R extends HashMap<String, Object> {
  private static final long serialVersionUID = 1L;

  public static R errorTable(String tableName, List<?> data, Map<String, String> title) {
    String isExport = getHttpServletRequest().getParameter("isExport");
    if ("0".equals(isExport)){
      HashMap<String, Object> stringObjectHashMap = new HashMap<>(4);
      stringObjectHashMap.put("tableName", tableName);
      stringObjectHashMap.put("title", title);
      stringObjectHashMap.put("items", data);
      R r = new R();
      r.put("code", "1001");
      r.put("data", stringObjectHashMap);
      return r;
    }else{
      excel(tableName,data,title);
      return new R();
    }
  }

  public static void excel(String excelName, List<?> data, Map<String, String> title) {
    HttpServletResponse response = getHttpServletResponse();
    List<ExcelExportEntity> beanList = new ArrayList<>();
    title.forEach(
        (k, v) -> {
          beanList.add(new ExcelExportEntity(k, v));
        });
    writeExcelToStream(excelName, beanList, data, response);
  }

  private static HttpServletResponse getHttpServletResponse() {
    ServletRequestAttributes attr =
        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    assert attr != null;
    HttpServletResponse response = attr.getResponse();
    return response;
  }

  private static HttpServletRequest getHttpServletRequest() {
    ServletRequestAttributes attr =
            (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    assert attr != null;
    HttpServletRequest request = attr.getRequest();
    return request;
  }

  /**
   * 写入excel到流
   *
   * @param response
   * @param fileName
   */
  private static <T> void writeExcelToStream(
      String fileName,
      List<ExcelExportEntity> entity,
      List<T> dtoLst,
      HttpServletResponse response) {
    try {
      // easyPoi操作excel
      Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), entity, dtoLst);
      // 设置响应头
      response.setHeader("content-Type", "application/vnd.ms-excel;charset=UTF-8");
      response.setHeader(
          "Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
      // 写入到流中
      ServletOutputStream outputStream = response.getOutputStream();
      workbook.write(outputStream);
      workbook.close();
      outputStream.close();
    } catch (Exception e) {
      //            log.error(e.getMessage());
      try {
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = response.getWriter();
        pw.write("导出文件异常！");
      } catch (Exception e1) {
        //                log.error(e1.getMessage());
      }
    }
  }
}
