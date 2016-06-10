package com.dutytrail.service.duty.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DutyService {

    @Value("${ping.alive}")
    private String configPingAlive;

    @RequestMapping(method = RequestMethod.GET, value = "/ping", produces = "application/json")
    public String ping() {
        return "Duty Service Alive. Profile in use: "+this.configPingAlive;
    }

}