package com.dtc233.redisqueue.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * @Author 丁廷宠 413778746@qq.com
 * @Describe
 * @Date： 2022/6/24 11:40
 * Copyright(c)2018-2021 Livolo All rights reserved.
 */

@Slf4j
@Configuration
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class RedisSubscribe {
    //  @Bean
    //  public RedisMessageListenerContainer redisMessageListenerContainer(
    //      RedisConnectionFactory redisConnectionFactory) {
    //    RedisMessageListenerContainer container = new RedisMessageListenerContainer();
    //    container.setConnectionFactory(redisConnectionFactory);
    //    // 将消息侦听器添加到（可能正在运行的）容器中。 如果容器正在运行，则侦听器会尽快开始接收（匹配）消息。
    //    // a 订阅了 topica、topicb 两个 频道
    //    container.addMessageListener(aConsumerRedisListener, new PatternTopic("topica"));
    //    container.addMessageListener(aConsumerRedisListener, new PatternTopic("topicb"));
    //
    //    // b 只订阅了 topicb  频道
    //    container.addMessageListener(bConsumerRedisListener, new PatternTopic("topicb"));
    //    return container;
    //  }
}
