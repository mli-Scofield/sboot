package com.dtc233.enumleran;

import com.dtc233.enumleran.constant.EndpointConstant;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * @Author 丁廷宠 413778746@qq.com
 * @Describe
 * @Date： 2022/2/24 4:01 下午
 * Copyright(c)2018-2021 Livolo All rights reserved.
 */
public class EndPointTest {
    public static void main(String[] args) throws UnknownHostException {
//        Map<String, EndpointConstant> areaMap = EndpointConstant.areaMap;
//        System.out.println(areaMap.get("eu-central-1").getAMQPPush());
        InetAddress addr = null;
        String address = "";
        addr = InetAddress.getByName("https://www.baidu.com");
        System.out.println(addr);
        //获得机器名称
        address = addr.getHostName();
        System.out.println(address);
    }
}
