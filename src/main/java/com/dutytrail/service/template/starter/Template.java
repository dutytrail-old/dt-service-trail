package com.dutytrail.service.template.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@EnableEurekaClient
@ComponentScan(basePackages = {"com.dutytrail.service.template"})
@EnableFeignClients(basePackages = {"com.dutytrail.service.template"})
public class Template {

    public static void main(String[] args) {
        SpringApplication.run(Template.class, args);
    }

}