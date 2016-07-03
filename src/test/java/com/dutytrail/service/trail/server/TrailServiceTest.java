package com.dutytrail.service.trail.server;

import com.dutytrail.service.trail.dao.TrailDAO;
import com.dutytrail.service.trail.entity.Status;
import com.dutytrail.service.trail.starter.Trail;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.postgresql.util.PSQLException;
import org.postgresql.util.PSQLState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@ContextConfiguration(classes = Trail.class, loader = SpringApplicationContextLoader.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebIntegrationTest({"eureka.client.enabled=false","spring.cloud.config.enabled=false"})
public class TrailServiceTest {

    @Autowired
    private TrailService trailService;

    @Mock
    private TrailDAO trailDAO;

    @Before
    public void setUp(){
        initMocks(this);
    }

    @Test
    public void pingTest() {
        String pingResponse = this.trailService.ping();
        Assert.assertNotNull(pingResponse);
        Assert.assertEquals("Trail Service Alive. Profile in use: Config properties loaded from LOCAL profile", pingResponse);
    }

    @Test
    public void postTrailTest() throws IOException {

    }

    @Test
    public void getTrailTest(){

    }

    @Test
    public void deleteTest(){

    }

}
