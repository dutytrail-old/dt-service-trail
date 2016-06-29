package com.dutytrail.service.trail.server;

import com.dutytrail.service.trail.dao.TrailDAO;
import com.dutytrail.service.trail.entity.Status;
import com.dutytrail.service.trail.entity.Trail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@RestController
public class TrailService {

    @Value("${ping.alive}")
    private String configPingAlive;

    @Autowired private TrailDAO trailDAO;

    @RequestMapping(method = RequestMethod.GET, value = "/ping", produces = "application/json")
    public String ping() {
        return "Trail Service Alive. Profile in use: "+this.configPingAlive;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/trail/{userId}/{dutyId}/{status}", produces = MediaType.APPLICATION_JSON)
    public Long postTrail(@PathVariable("userId") Long userId, @PathVariable("dutyId") Long dutyId, @PathVariable("status") String status) {
        return this.trailDAO.postTrail(userId, dutyId, Status.get(status));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/trail/{dutyId}", produces = MediaType.APPLICATION_JSON)
    public ArrayList<Trail> getTrail(@PathVariable("dutyId") Long dutyId){
        return this.trailDAO.getTrail(dutyId);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/trail/{dutyId}", produces = MediaType.APPLICATION_JSON)
    public Long delete(@PathVariable("dutyId") Long dutyId){
        return this.trailDAO.deleteTrail(dutyId);
    }

}