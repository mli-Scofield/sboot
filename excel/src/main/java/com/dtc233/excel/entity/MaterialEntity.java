package com.dtc233.excel.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @Author 丁廷宠 413778746@qq.com
 * @Describe
 * @Date： 2022/8/12 10:49
 * Copyright(c)2018-2021 Livolo All rights reserved.
 */
@Data
public class MaterialEntity {
    @Excel(name = "物料编码")
    private String code;
    @Excel(name = "物料名称")
    private String name;
}
