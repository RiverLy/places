package com.river.places.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@TestConfiguration
public class TestRedisConfiguration {

    private int redisPort = 63791;

    private RedisServer redisServer;

    @PostConstruct
    public void redisServer() throws IOException {
	redisServer = new RedisServer(redisPort);
	redisServer.start();
    }

    @PreDestroy
    public void stopRedis() {
	if (redisServer != null) {
	    redisServer.stop();
	}
    }

}
