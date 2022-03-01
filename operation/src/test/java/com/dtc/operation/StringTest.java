package com.dtc.operation;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 丁廷宠 413778746@qq.com
 * @Describe
 * @Date： 2022/2/21 11:25 上午
 * Copyright(c)2018-2021 Livolo All rights reserved.
 */
public class StringTest {
    public static void main(String[] args) {
        List<Integer> one = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            one.add(i + 1);
        }
        System.out.println(one);
        for (int i = 0; i < one.size(); i++) {
            if (one.get(i) == 3) {
                one.remove(i--);
            }
            System.out.println(i);
            System.out.println(one);
        }
        System.out.println(one);
    }
}
