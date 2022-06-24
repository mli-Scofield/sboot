package com.dtc233.redisqueue.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;
import java.time.Duration;

/**
 * @Author 丁廷宠 413778746@qq.com
 * @Describe
 * @Date： 2022/5/30 14:09
 * Copyright(c)2018-2021 Livolo All rights reserved.
 */
@Configuration
@EnableCaching
@RequiredArgsConstructor
@Data
public class RedisConfig {

  /** 默认两小时 */
  private static final long DURATION_SECOND_7200 = 7200L;

  @Bean
  public RedisTemplate<String, Serializable> redisTemplate(
      RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, Serializable> redisTemplate = new RedisTemplate<>();
    redisTemplate.setKeySerializer(keySerializer());
    redisTemplate.setHashKeySerializer(keySerializer());
    redisTemplate.setHashValueSerializer(valueSerializer());
    redisTemplate.setValueSerializer(valueSerializer());
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    return redisTemplate;
  }


  @Bean
  public HashOperations<String, String, Object> hashOperations(
      RedisTemplate<String, Serializable> redisTemplate) {
    return redisTemplate.opsForHash();
  }

  @Bean
  public ValueOperations<String, Serializable> valueOperations(
      RedisTemplate<String, Serializable> redisTemplate) {
    return redisTemplate.opsForValue();
  }

  @Bean
  public ListOperations<String, Serializable> listOperations(
      RedisTemplate<String, Serializable> redisTemplate) {
    return redisTemplate.opsForList();
  }

  @Bean
  public SetOperations<String, Serializable> setOperations(
      RedisTemplate<String, Serializable> redisTemplate) {
    return redisTemplate.opsForSet();
  }

  @Bean
  public ZSetOperations<String, Serializable> zSetOperations(
      RedisTemplate<String, Serializable> redisTemplate) {
    return redisTemplate.opsForZSet();
  }

  @Bean
  public StreamOperations<String, String, Serializable> streamOperations(
      RedisTemplate<String, Serializable> redisTemplate) {
    return redisTemplate.opsForStream();
  }

  /**
   * 默认的缓存管理，存放时效较长的缓存
   *
   * @param redisConnectionFactory redis连接工厂
   * @return CacheManager
   */
  @Primary
  @Bean
  public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
    RedisCacheConfiguration config =
        RedisCacheConfiguration.defaultCacheConfig()
            // 过期时间
            .entryTtl(Duration.ZERO)
            // 不缓存null值
            .disableCachingNullValues()
            // 明确manager中的序列化与template一样，防止莫名其妙的问题
            .serializeKeysWith(
                RedisSerializationContext.SerializationPair.fromSerializer(this.keySerializer()))
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(this.valueSerializer()));

    return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(config).transactionAware().build();
  }

  /**
   * 存放时效较短的缓存（5分钟）
   *
   * @param redisConnectionFactory redis连接工厂
   * @return CacheManager
   */
  @SuppressWarnings({"Duplicates"})
  @Bean
  public CacheManager cacheManagerInTwoHour(RedisConnectionFactory redisConnectionFactory) {
    RedisCacheConfiguration config =
        RedisCacheConfiguration.defaultCacheConfig()
            // 过期时间
            .entryTtl(Duration.ofSeconds(DURATION_SECOND_7200))
            // 不缓存null值
             .disableCachingNullValues()
            // 明确manager中的序列化与template一样，防止莫名其妙的问题
            .serializeKeysWith(
                RedisSerializationContext.SerializationPair.fromSerializer(this.keySerializer()))
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(this.valueSerializer()));

    return RedisCacheManager.builder(redisConnectionFactory)
        .cacheDefaults(config)
        .transactionAware()
        .build();
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
