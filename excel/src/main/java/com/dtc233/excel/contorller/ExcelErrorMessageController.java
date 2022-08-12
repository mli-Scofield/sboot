package com.dtc233.excel.contorller;

import com.dtc233.excel.common.ExceptionTable;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @Author 丁廷宠 413778746@qq.com
 * @Describe
 * @Date： 2022/8/12 10:49
 * Copyright(c)2018-2021 Livolo All rights reserved.
 */
@Controller
@RequestMapping(value = "error")
public class ExcelErrorMessageController {
  /**
   * 导出报表
   *
   * @return
   */
  @RequestMapping(value = "/export")
  @ResponseBody
  public void export(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map<String, String> objectObjectLinkedHashMap = new HashMap<>();
    objectObjectLinkedHashMap.put("物料比那好", "materialCode");
    List<Map<String,String>> mapList = new ArrayList<>();
    Map<String,String> map = new HashMap<>();
    map.put("materialCode","kkyy");
    mapList.add(map);
    throw new ExceptionTable("缺少工价的物料",objectObjectLinkedHashMap,mapList);
  }
}
