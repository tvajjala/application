package com.tvajjala.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.config.ManagementCenterConfig;
import com.hazelcast.config.MulticastConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

/**
 *
 * @author ThirupathiReddy V Explore more :
 *         https://blog.hazelcast.com/spring-boot/
 *
 *         More About Caching
 *
 *         https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-caching.html
 */
@Configuration
@EnableCaching
@Profile("hazelcast")
public class HazelcastCacheConfig extends CachingConfigurerSupport {

    /** Reference to logger */
    private static final Logger LOG = LoggerFactory.getLogger(HazelcastCacheConfig.class);

    @Bean
    public HazelcastInstance hazelcastInstance() {
	final Config config = new Config();

	final ManagementCenterConfig  managementCenterConfig=new ManagementCenterConfig();
	managementCenterConfig.setEnabled(true);
	managementCenterConfig.setUrl("http://localhost:8888/mancenter");// you need to deploy man center in separate server and point that URL here
	config.setManagementCenterConfig(managementCenterConfig);
	final GroupConfig groupConfig = new GroupConfig();
	groupConfig.setName("dev");
	groupConfig.setPassword("dev");
	config.setGroupConfig(groupConfig);
	LOG.info("Creating hazelcast cluster node instance with groupName {} ", groupConfig.getName());
	final NetworkConfig networkConfig = config.getNetworkConfig();
	final MulticastConfig multicastConfig = new MulticastConfig();
	multicastConfig.setEnabled(false);
	networkConfig.getJoin().setMulticastConfig(multicastConfig);
	networkConfig.getJoin().getAwsConfig().setEnabled(false);
	networkConfig.getJoin().getTcpIpConfig().setEnabled(true);

	final HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(config);
	return hazelcastInstance;
    }

    @Bean
    @DependsOn("hazelcastInstance")// This you can externalize later as standalone hazelcast Server
    HazelcastInstance hazelcastClient() {
	final ClientConfig clientConfig = new ClientConfig();
	clientConfig.setGroupConfig(new GroupConfig("dev","dev"));
	//clientConfig.setLoadBalancer(yourLoadBalancer);
	return HazelcastClient.newHazelcastClient(clientConfig);
    }

    @Bean
    public CacheManager cacheManager(HazelcastInstance hazelcastClient) {
	return new com.hazelcast.spring.cache.HazelcastCacheManager(hazelcastClient);
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
