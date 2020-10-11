package com.jfbian.core.threadpool;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 麻木神
 * @version 1.0
 * @title ThreadPoolProperties.java
 * @description 线程池参数维护
 * @time 2019年12月4日 下午4:18:45
 */

@Component
@ConfigurationProperties(prefix = "thread.pool")
@Getter
@Setter
public class ThreadPoolProperties {

    private int corePoolSize;

    private int maxPoolSize;

    private int queueCapacity;

    private int keepAliveSeconds;

}

