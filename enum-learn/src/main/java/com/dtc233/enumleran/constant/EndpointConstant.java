package com.dtc233.enumleran.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author 丁廷宠 413778746@qq.com
 * @Describe
 * @Date： 2022/2/24 2:44 下午
 * Copyright(c)2018-2021 Livolo All rights reserved.
 */

public enum EndpointConstant {
    /**
     * 欧洲
     */
    EU("eu-central-1",
            "https://eu-central-1.api-iot.aliyuncs.com",
            "amqps://ilop.iot-amqp.eu-central-1.aliyuncs.com:5671",
            "https://ilop.iot-as-http2.eu-central-1.aliyuncs.com:443"),
    /**
     * 亚洲
     */
    AP("ap-southeast-1",
            "https://ap-southeast-1.api-iot.aliyuncs.com",
            "amqps://ilop.iot-amqp.ap-southeast-1.aliyuncs.com:5671 ",
            "https://ilop.iot-as-http2.ap-southeast-1.aliyuncs.com:443"),
    /**
     * 中国
     */
    CN("cn-shanghai",
            "https://api.link.aliyun.com",
            "amqps://ilop.iot-amqp.cn-shanghai.aliyuncs.com:5671",
            "https://ilop.iot-as-http2.cn-shanghai.aliyuncs.com:443"),

    /**
     * 美国
     */
    US("us-east-1",
            "https://us-east-1.api-iot.aliyuncs.com",
            "amqps://ilop.iot-amqp.us-east-1.aliyuncs.com:5671",
            "https://ilop.iot-as-http2.us-east-1.aliyuncs.com:443"
    );

    private final String area;
    private final String AMQPPush;
    private final String HTTPPush;
    private final String cloudApi;

    EndpointConstant(String area, String cloudApi, String AMQPPush, String HTTPPush) {
        this.area = area;
        this.cloudApi = cloudApi;
        this.AMQPPush = AMQPPush;
        this.HTTPPush = HTTPPush;
    }

    public String getArea() {
        return area;
    }

    public String getAMQPPush() {
        return AMQPPush;
    }

    public String getHTTPPush() {
        return HTTPPush;
    }

    public String getCloudApi() {
        return cloudApi;
    }

    public static Map<String, EndpointConstant> areaMap = new HashMap<>();

    static {
        EndpointConstant[] types = EndpointConstant.values();
        for (EndpointConstant type : types) {
            areaMap.put(String.valueOf(type.area), type);
        }
    }
}
