package br.com.aws_demo.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AsyncConfig {

    @Bean
    ExecutorService taskExecutor() {
        return Executors.newFixedThreadPool(5);
    }
}