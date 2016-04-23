package com.dutytrail.service.duty.starter;

import com.dutytrail.service.duty.server.DutyService;
import com.netflix.config.ConfigurationManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

@Configuration
@EnableAutoConfiguration
@EnableEurekaClient
@ComponentScan(basePackages = {"com.dutytrail.service.duty"})
@EnableFeignClients(basePackages = {"com.dutytrail.service.duty"})
public class Duty {

    public static void main(String[] args) {
        configureHystrix();
        SpringApplication.run(Duty.class, args);
    }

    private static void configureHystrix() {
        for (Method method : DutyService.class.getMethods()) {
            ConfigurationManager.getConfigInstance().setProperty(String.format("hystrix.command.%s.execution.isolation.thread.timeoutInMilliseconds", method.getName()), 150000);
        }
    }

}