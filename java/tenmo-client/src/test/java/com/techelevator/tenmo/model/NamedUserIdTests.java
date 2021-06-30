package com.techelevator.tenmo.model;

import org.junit.Assert;
import org.junit.Test;

public class NamedUserIdTests
{
    @Test
    public void testSetGetUsernameID() {
        NamedUserId testNamedUserId = new NamedUserId();

        int expected1 = 100;
        String expected2 = "Captain Picard";

        testNamedUserId.setUserId(100);
        testNamedUserId.setUserName("Captain Picard");

        int result1 = testNamedUserId.getUserId();
        String result2 = testNamedUserId.getUserName();

        Assert.assertEquals(expected1,result1);
        Assert.assertEquals(expected2, result2);

    }

}
