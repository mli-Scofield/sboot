package com.dtc233.redisqueue.config;

import com.dtc233.redisqueue.Consumer.UserConsumer;
import com.dtc233.redisqueue.entity.LivUser;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.sun.istack.internal.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;

import java.io.Serializable;
import java.time.Duration;
import java.util.Collections;

/**
 * @Author 丁廷宠 413778746@qq.com @Describe
 * @Date： 2022/6/24 11:05
 * Copyright(c)2018-2021 Livolo All rights reserved.
 */
@Slf4j
@Configuration
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class RedisStreamConfig {
  /**
   * 用户
   */
  private final UserConsumer userConsumer;

  @Bean
  public StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, MapRecord<String, Object, Object>>
      steamListenerContainerOptions() {
    return StreamMessageListenerContainer.StreamMessageListenerContainerOptions.builder()
        // block读取超时时间
        .pollTimeout(Duration.ofSeconds(3))
        // count 数量（一次只获取一条消息）
        .batchSize(1)
        // 序列化规则 这里的设置好像没用、会被redisTemplate 的设置覆盖掉
        .serializer(keySerializer())
        .hashKeySerializer(keySerializer())
        .hashValueSerializer(valueSerializer())
        .build();
  }

  @Bean
  public StreamMessageListenerContainer<String, MapRecord<String, Object, Object>>
      streamMessageListenerContainer(
          @NotNull RedisConnectionFactory redisConnectionFactory,
          StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, MapRecord<String, Object, Object>>
              streamMessageListenerContainerOptions,
          RedisTemplate<String, Serializable> redisTemplate) {
    StreamMessageListenerContainer<String, MapRecord<String, Object, Object>> listenerContainer =
        StreamMessageListenerContainer.create(
            redisConnectionFactory, streamMessageListenerContainerOptions);
    // 创建流程
    if (Boolean.FALSE.equals(redisTemplate.hasKey(userConsumer.STREAM_NAME))) {
      LivUser livUser = new LivUser();
      livUser.setUserName("libai");
      livUser.setPassword("dufu");
      redisTemplate.opsForStream().add(userConsumer.STREAM_NAME, Collections.singletonMap("uu",livUser));
      log.info("初始化stream {} success", userConsumer.STREAM_NAME);
    }
    // 创建消费者组
    try {
      redisTemplate.opsForStream().createGroup(userConsumer.STREAM_NAME, userConsumer.GROUP_NAME);
    } catch (Exception e) {
      log.info("消费者组 {} 已存在", userConsumer.GROUP_NAME);
    }

    // 注册消费者 消费者名称，从哪条消息开始消费，消费者类
    // > 表示没消费过的消息
    // $ 表示最新的消息
    listenerContainer.receive(
        Consumer.from(userConsumer.GROUP_NAME, userConsumer.CONSUMER_NAME),
        StreamOffset.create(userConsumer.STREAM_NAME, ReadOffset.lastConsumed()),
        userConsumer);

    listenerContainer.start();
    return listenerContainer;
  }

  private RedisSerializer<String> keySerializer() {
    return new StringRedisSerializer();
  }

  /**
   * DefaultTyping AVA_LANG_OBJECT: 当对象属性类型为Object时生效；
   * OBJECT_AND_NON_CONCRETE:当对象属性类型为Object或者非具体类型（抽象类和接口）时生效；
   * NON_CONCRETE_AND+_ARRAYS: 同上, 另外所有的数组元素的类型都是非具体类型或者对象类型；
   * NON_FINAL: 对所有非final类型或者非final类型元素的数组。
   *
   * @return redis值序列化
   */
  private RedisSerializer<Object> valueSerializer() {
    Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer =
            new Jackson2JsonRedisSerializer<>(Object.class);
    ObjectMapper om = new ObjectMapper();
    om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    //        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    om.activateDefaultTyping(
            LaissezFaireSubTypeValidator.instance,
            ObjectMapper.DefaultTyping.NON_FINAL,
            JsonTypeInfo.As.WRAPPER_ARRAY);
    // 略过不匹配的属性
    om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    jackson2JsonRedisSerializer.setObjectMapper(om);
    return jackson2JsonRedisSerializer;
  }
}
