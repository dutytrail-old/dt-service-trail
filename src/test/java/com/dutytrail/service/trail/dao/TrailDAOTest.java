package com.dutytrail.service.trail.dao;

import com.dutytrail.service.trail.entity.Status;
import com.dutytrail.service.trail.starter.Trail;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = Trail.class, loader = SpringApplicationContextLoader.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebIntegrationTest({"eureka.client.enabled=false","spring.cloud.config.enabled=false"})
public class TrailDAOTest {

    @Mock Connection mockedCon;
    @Mock PreparedStatement mockedPreparedStatement;
    @Mock ResultSet mockedResultSet;

    @Autowired TrailDAO trailDAO;

    @Before
    public void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);
        doNothing().when(this.mockedCon).commit();
        when(this.mockedCon.prepareStatement(Mockito.anyString())).thenReturn(this.mockedPreparedStatement);
        doNothing().when(this.mockedPreparedStatement).setString(Mockito.anyInt(), Mockito.anyString());
        doNothing().when(this.mockedPreparedStatement).setLong(Mockito.anyInt(), Mockito.anyLong());
        when(this.mockedPreparedStatement.executeUpdate()).thenReturn(1);
        when(this.mockedPreparedStatement.executeQuery()).thenReturn(this.mockedResultSet);
        when(this.mockedResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(this.mockedResultSet.getLong(Mockito.anyString())).thenReturn(1L);
        when(this.mockedResultSet.getString(Mockito.anyString())).thenReturn("status");
        when(this.mockedResultSet.getTimestamp(Mockito.anyString())).thenReturn(new Timestamp(Calendar.getInstance().getTime().getTime()));

        ReflectionTestUtils.setField(trailDAO, "con", this.mockedCon);
        ReflectionTestUtils.setField(trailDAO, "ps", this.mockedPreparedStatement);
        ReflectionTestUtils.setField(trailDAO, "resultSet", this.mockedResultSet);
    }

    @Test
    public void postTrailTest() throws SQLException {
        Long postTrailSQLResponse = this.trailDAO.postTrail(1L, 1L, Status.DONE);
        Assert.assertNotNull(postTrailSQLResponse);
        Assert.assertTrue(1L == postTrailSQLResponse);
    }

    @Test
    public void postTrailFailTest() throws SQLException {
        when(this.mockedPreparedStatement.executeUpdate()).thenReturn(0);
        Long postTrailSQLResponse = this.trailDAO.postTrail(1L, 1L, Status.DONE);
        Assert.assertNotNull(postTrailSQLResponse);
        Assert.assertTrue(-1L == postTrailSQLResponse);
    }

    @Test(expected = SQLException.class)
    public void postTrailExceptionTest() throws SQLException {
        when(this.mockedPreparedStatement.executeUpdate()).thenThrow(new SQLException());
        this.trailDAO.postTrail(1L, 1L, Status.DONE);
    }

    @Test
    public void getTrailTest() throws SQLException {
        ArrayList<com.dutytrail.service.trail.entity.Trail> trails = this.trailDAO.getTrail(1L);
        Assert.assertNotNull(trails);
        Assert.assertEquals(1, trails.size());
    }

    @Test(expected = SQLException.class)
    public void getTrailExceptionTest() throws SQLException {
        when(this.mockedResultSet.next()).thenThrow(new SQLException());
        this.trailDAO.getTrail(1L);
    }

    @Test
    public void deleteTrailTest() throws SQLException {
        Long deleteTrailSQLResponse = this.trailDAO.deleteTrail(1L);
        Assert.assertNotNull(deleteTrailSQLResponse);
        Assert.assertTrue(1L == deleteTrailSQLResponse);
    }

    @Test
    public void deleteTrailFailTest() throws SQLException {
        when(this.mockedPreparedStatement.executeUpdate()).thenReturn(0);
        Long deleteTrailSQLResponse = this.trailDAO.deleteTrail(1L);
        Assert.assertNotNull(deleteTrailSQLResponse);
        Assert.assertTrue(-1L == deleteTrailSQLResponse);
    }

    @Test(expected = SQLException.class)
    public void deleteTrailExceptionTest() throws SQLException {
        when(this.mockedPreparedStatement.executeUpdate()).thenThrow(new SQLException());
        this.trailDAO.deleteTrail(1L);
    }
}
