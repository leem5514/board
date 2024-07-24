package com.beyond.board.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    // application.yml의 spring.redis.host의 정보를 소스코드의 변수로 가지고 오는 것

    @Value("${spring.redis.host}")
    public String host;

    @Value("${spring.redis.port}")
    public int port;


    @Bean
    // redis connectionFactory는 Redis서버와의 연결을 설정하는 역할
    // LettuceConnectionFactory는 RedisConnectionFactory 으로서 구현체로서 실질적 역할 수행
    public RedisConnectionFactory redisConnectionFactory() {
//        return new LettuceConnectionFactory(host, port);
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(host);
        configuration.setPort(port);
//        configuration.setDatabase(2);
//        configuration.getPassword("1234");
        return new LettuceConnectionFactory(configuration);
    }
    //redis Template Redis 와 상호작용 redis redis.key , value의 형식 변호ㅑㅏ
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }
}
