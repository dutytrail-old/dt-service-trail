package com.dutytrail.service.duty.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrailService {

    @Value("${ping.alive}")
    private String configPingAlive;

    @RequestMapping(method = RequestMethod.GET, value = "/ping", produces = "application/json")
    public String ping() {
        return "Trail Service Alive. Profile in use: "+this.configPingAlive;
    }

}