package com.dtc233.redisqueue.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author 丁廷宠 413778746@qq.com
 * @Describe
 * @Date： 2022/5/30 14:38
 * Copyright(c)2018-2021 Livolo All rights reserved.
 */
@Data
public class LivUser implements Serializable {
    private String userName;
    private String password;
}
