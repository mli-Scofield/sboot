package com.dtc233.flyway.datasource.annotation;

import java.lang.annotation.*;

/**
 * @Author 丁廷宠 413778746@qq.com
 * @Describe
 * @Date： 2022/2/23 10:21 上午
 * Copyright(c)2018-2021 Livolo All rights reserved.
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DataSource {
    String value() default "";
}
