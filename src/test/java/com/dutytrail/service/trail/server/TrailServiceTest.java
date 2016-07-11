package com.dutytrail.service.trail.server;

import com.dutytrail.service.trail.dao.TrailDAO;
import com.dutytrail.service.trail.entity.Status;
import com.dutytrail.service.trail.starter.Trail;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import static org.mockito.Mockito.when;

@ContextConfiguration(classes = Trail.class, loader = SpringApplicationContextLoader.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebIntegrationTest({"eureka.client.enabled=false","spring.cloud.config.enabled=false"})
public class TrailServiceTest {

    @Autowired private TrailService trailService;
    @Mock private TrailDAO trailDAO;
    private ArrayList<com.dutytrail.service.trail.entity.Trail> fakeTrails;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(trailService, "trailDAO", this.trailDAO);
        this.fakeTrails = new ArrayList<>();
        this.fakeTrails.add(new com.dutytrail.service.trail.entity.Trail(1L, "done", new Timestamp(Calendar.getInstance().getTime().getTime())));
    }

    @After
    public void tearDown(){
        this.trailService = null;
        this.trailDAO = null;
        this.fakeTrails = null;
    }

    @Test
    public void pingTest() {
        String pingResponse = this.trailService.ping();
        Assert.assertNotNull(pingResponse);
        Assert.assertEquals("Trail Service Alive. Profile in use: Config properties loaded from LOCAL profile", pingResponse);
    }

    @Test
    public void postTrailTest() throws IOException, SQLException {
        when(trailDAO.postTrail(1L, 1L, Status.DONE)).thenReturn(2L);
        Long postResponse = this.trailService.postTrail(1L, 1L, Status.DONE.getDescription());
        Assert.assertNotNull(postResponse);
        Assert.assertTrue(2L == postResponse);
    }

    @Test
    public void getTrailTest() throws SQLException {
        when(trailDAO.getTrail(Mockito.anyLong())).thenReturn(this.fakeTrails);
        ArrayList<com.dutytrail.service.trail.entity.Trail> trails = this.trailService.getTrail(1L);
        Assert.assertNotNull(trails);
        Assert.assertEquals(1, trails.size());
        Assert.assertTrue(1L == trails.get(0).getUserId());
        Assert.assertEquals("done", trails.get(0).getStatus());
        Assert.assertEquals(Status.get(trails.get(0).getStatus()).getDescription(), trails.get(0).getStatus());
        Assert.assertNotNull(trails.get(0).getTimestamp());
    }

    @Test
    public void deleteTest() throws SQLException {
        when(trailDAO.deleteTrail(Mockito.anyLong())).thenReturn(2L);
        Long deleteResponse = this.trailService.delete(1L);
        Assert.assertNotNull(deleteResponse);
        Assert.assertTrue(2L == deleteResponse);
    }

}
