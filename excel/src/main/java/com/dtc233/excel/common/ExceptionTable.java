package com.dtc233.excel.common;

import lombok.*;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author 丁廷宠 413778746@qq.com @Describe 返回table类型的异常 @Date： 2022/8/12 11:10 Copyright(c)2018-2021
 * Livolo All rights reserved.
 */
@Data
public class ExceptionTable extends RuntimeException {
  private int code = 200;

  private String msg;

  private List<?> data;

  private Map<String, String> title;

  public ExceptionTable(String msg, Map<String, String> title, List<?> data) {
    this.msg = msg;
    this.title = title;
    this.data = data;
  }
}
