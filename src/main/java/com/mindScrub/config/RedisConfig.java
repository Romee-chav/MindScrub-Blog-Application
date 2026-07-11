package com.mindScrub.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
public class RedisConfig {

	@Bean
	public RedisCacheManager redisCacheManager (RedisConnectionFactory redisConnectionFactory) {
		RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10));
		
		Map<String,RedisCacheConfiguration> cacheConfiguration = new HashMap<>();
		
		cacheConfiguration.put("categories", defaultConfig.entryTtl(Duration.ofMinutes(30)));
		cacheConfiguration.put("all-blog", defaultConfig.entryTtl(Duration.ofMinutes(2)));
		
		return RedisCacheManager.builder(redisConnectionFactory)
				.cacheDefaults(defaultConfig)
				.withInitialCacheConfigurations(cacheConfiguration)
				.build();
	}
}
