package com.river.places.common.redis.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;

@EnableCaching
@Configuration
public class RedisCacheConfig {
    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
	return RedisCacheConfiguration.defaultCacheConfig()
				      .entryTtl(Duration.ofMinutes(15))
				      .disableCachingNullValues();
    }

    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
	return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(redisCacheConfiguration()).build();
    }

}
