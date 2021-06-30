package com.techelevator.tenmo.model;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class TransferSendTest
{
    @Test
    public void testTransferSendWithParams()
    {
        TransferSend testTransferSend;
        testTransferSend = new TransferSend(100,200,new BigDecimal("123.45"));

        int expected1 = 100;
        int expected2 = 200;
        BigDecimal expected3 = new BigDecimal("123.45");

        int result1 = testTransferSend.getFromUserId();
        int result2 = testTransferSend.getToUserId();
        BigDecimal result3 = testTransferSend.getAmountToSend();

        Assert.assertEquals(expected1,result1);
        Assert.assertEquals(expected2,result2);
        Assert.assertEquals(expected3,result3);
    }

    @Test
    public void testSetBlankTransferSend()
    {
        TransferSend testTransferSend = new TransferSend();

        int expected1 = 100;
        int expected2 = 200;
        BigDecimal expected3 = new BigDecimal("123.45");

        testTransferSend.setFromUserId(100);
        testTransferSend.setToUserId(200);
        testTransferSend.setAmountToSend(new BigDecimal("123.45"));

        int result1 = testTransferSend.getFromUserId();
        int result2 = testTransferSend.getToUserId();
        BigDecimal result3 = testTransferSend.getAmountToSend();

        Assert.assertEquals(expected1,result1);
        Assert.assertEquals(expected2,result2);
        Assert.assertEquals(expected3,result3);
    }
}
