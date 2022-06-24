package com.dtc233.redisqueue.Consumer;

import com.alibaba.fastjson.JSONObject;
import com.dtc233.redisqueue.entity.LivUser;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.PendingMessages;
import org.springframework.data.redis.connection.stream.PendingMessagesSummary;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StreamOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Author 丁廷宠 413778746@qq.com @Describe @Date： 2022/6/24 10:55 Copyright(c)2018-2021 Livolo All
 * rights reserved.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserConsumer implements StreamListener<String, MapRecord<String, Object, Object>> {
  /** 队列名 */
  public final String STREAM_NAME = "userStream";
  /** 队列组名 */
  public final String GROUP_NAME = "userGroup";
  /** 消费者名 */
  public final String CONSUMER_NAME = "userConsumer";

  private final RedisTemplate<String, Serializable> redisTemplate;

  @Override
  public void onMessage(MapRecord<String, Object, Object> message) {
    log.info(String.valueOf(message.getValue()));
    Map<Object, Object> value = message.getValue();
    // 解析消息
    Optional.ofNullable(value.get("value"))
        .ifPresent(
            x -> {
              try {
                //        byte[] bytes = x.toString().getBytes(StandardCharsets.UTF_8);
                //        Jackson2JsonRedisSerializer<LivUser> jackson2JsonRedisSerializer = new
                // Jackson2JsonRedisSerializer<>(LivUser.class);
                //        LivUser deserialize = jackson2JsonRedisSerializer.deserialize(bytes);
                LivUser livUser = (LivUser) x;
                log.info(JSONObject.toJSONString(livUser));
                // 消息应答
                redisTemplate.opsForStream().acknowledge(STREAM_NAME, GROUP_NAME, message.getId());
              } catch (Exception ex) {
                // 消费异常导致未能ack时，消息会进入pending列表，我们可以启动定时任务来读取pending列表处理失败的任务
                ex.printStackTrace();
              }
            });
    // 完全解析失败、就进行废弃
    // 无法被解析的数据、直接删除掉 记录日志
    log.error("无法被解析的消息-------");
    log.error(JSONObject.toJSONString(message));
    redisTemplate.opsForStream().delete(STREAM_NAME, message.getId());
  }

  @Scheduled(fixedRate = 1000)
  public void pending() {
    PendingMessagesSummary pending = redisTemplate.opsForStream().pending(STREAM_NAME, GROUP_NAME);
    PendingMessages pendingMessages =
        redisTemplate.opsForStream().pending(STREAM_NAME, GROUP_NAME, Range.closed("0", "+"), 10);
    pendingMessages.forEach(
        message -> {
          // 消息的ID
          RecordId recordId = message.getId();
          // 消息从消费组中获取，到此刻的时间
          Duration elapsedTimeSinceLastDelivery = message.getElapsedTimeSinceLastDelivery();
          // 消息被获取的次数
          long deliveryCount = message.getTotalDeliveryCount();

          // 通过streamOperations，直接读取这条pending消息，
          Optional.ofNullable(
                  redisTemplate
                      .opsForStream()
                      .range(
                          STREAM_NAME,
                          Range.rightOpen(message.getIdAsString(), message.getIdAsString())))
              .ifPresent(v -> {
                  MapRecord<String, Object, Object> entries = v.get(0);
              });
            log.error("无法被解析的消息-------");
            log.error(JSONObject.toJSONString(message));
            redisTemplate.opsForStream().delete(STREAM_NAME, message.getId());
        });
  }
}
