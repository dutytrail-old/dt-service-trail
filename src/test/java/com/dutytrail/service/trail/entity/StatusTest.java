package com.dutytrail.service.trail.entity;

import org.junit.Assert;
import org.junit.Test;

public class StatusTest {

    @Test
    public void getTest(){
        Assert.assertEquals("done", Status.get("done").getDescription());
        Assert.assertEquals("created", Status.get("created").getDescription());
        Assert.assertEquals("deleted", Status.get("deleted").getDescription());
        Assert.assertNull(Status.get("not existing"));
    }
}
