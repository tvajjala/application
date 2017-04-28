package com.tvajjala.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 *
 * @author ThirupathiReddy V Explore more :
 *         http://caseyscarborough.com/blog/2014/12/18/caching-data-in-spring-using-redis/
 *
 *         More About Caching
 *
 *         https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-caching.html
 *
 *         https://spring.io/guides/gs/messaging-redis/<br>
 *         to install redis server on mac<br>
 *         <ul>
 *         	<li>$/>brew install redis</li>
 *         	<li>$/>redis-server</li>
 *         </ul>
 *
 */
@Configuration
@EnableCaching
@Profile("redis")
public class RedisCacheConfig extends CachingConfigurerSupport {

    /** Reference to logger */
    private static final Logger LOG = LoggerFactory.getLogger(RedisCacheConfig.class);

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
	final JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();
	// Defaults
	redisConnectionFactory.setHostName("127.0.0.1");
	redisConnectionFactory.setPort(6379);
	return redisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory cf) {
	final RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
	redisTemplate.setConnectionFactory(cf);
	return redisTemplate;
    }

    @Bean
    public CacheManager cacheManager(RedisTemplate<String, String> redisTemplate) {
	final RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);

	// Number of seconds before expiration. Defaults to unlimited (0)
	cacheManager.setDefaultExpiration(1);
	return cacheManager;
    }

    @Override
    public KeyGenerator keyGenerator() {

	return (o, method, objects) -> {
	    // This will generate a unique key of the class name, the method
	    // name,
	    // and all method parameters appended.
	    final StringBuilder sb = new StringBuilder();
	    sb.append(o.getClass().getName());
	    sb.append(method.getName());
	    for (final Object obj : objects) {
		sb.append(obj.toString());
	    }

	    LOG.info("Cache Key  {} ", sb.toString());
	    return sb.toString();
	};
    }
}
