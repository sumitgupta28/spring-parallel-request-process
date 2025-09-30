package com.spring.parallel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class SpringAsyncConfig {

    @Bean(name = "threadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(5); // Minimum number of threads in the pool
        threadPoolTaskExecutor.setMaxPoolSize(10); // Maximum number of threads in the pool
        threadPoolTaskExecutor.setQueueCapacity(250); // Capacity of the queue for holding submitted tasks
        threadPoolTaskExecutor.setThreadNamePrefix("MyAsyncTask-"); // Prefix for the names of threads in the pool
        threadPoolTaskExecutor.initialize(); // Initializes the executor
        threadPoolTaskExecutor.setPrestartAllCoreThreads(true);
        return threadPoolTaskExecutor;
    }
}
