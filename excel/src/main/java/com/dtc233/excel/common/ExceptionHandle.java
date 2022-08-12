package com.dtc233.excel.common;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author 丁廷宠 413778746@qq.com
 * @Describe
 * @Date： 2022/8/12 14:05
 * Copyright(c)2018-2021 Livolo All rights reserved.
 */
@RestControllerAdvice
public class ExceptionHandle {
    /**
     * 返回简单的表单展示错误信息
     */
    @ExceptionHandler({ExceptionTable.class})
    public R handleExceptionTable(ExceptionTable e){
        return R.errorTable(e.getMsg(),e.getData(),e.getTitle());
    }

}
