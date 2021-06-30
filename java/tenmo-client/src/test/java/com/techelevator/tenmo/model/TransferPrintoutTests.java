package com.techelevator.tenmo.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class TransferPrintoutTests {
    TransferPrintOut transferPrintOut;

    @Before
    public void init()
    {
        transferPrintOut = new TransferPrintOut();
        Assert.assertNotNull(transferPrintOut);
    }

    @Test
    public void testTransferId()
    {
        int expected = 200;
        transferPrintOut.setTransferId(200);
        int result = transferPrintOut.getTransferId();
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testFromTo() {
        String expected = "From:";
        transferPrintOut.setFromTo("From:");
        String result = transferPrintOut.getFromTo();
        Assert.assertEquals(expected,result);
    }

    @Test
    public void testUserName() {
        String expected = "Mario";
        transferPrintOut.setUserName("Mario");
        String result = transferPrintOut.getUserName();
        Assert.assertEquals(expected,result);
    }

    @Test
    public void testAmount() {
        BigDecimal expected = new BigDecimal("666.20");
        transferPrintOut.setAmount(new BigDecimal("666.20"));
        BigDecimal result = transferPrintOut.getAmount();
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testGetAmountString() {
        transferPrintOut.setAmount(new BigDecimal("999.99"));
        String amountAsString = transferPrintOut.getAmountAsString();
        System.out.println(amountAsString);

        transferPrintOut.setAmount(new BigDecimal("66.66"));
        amountAsString = transferPrintOut.getAmountAsString();
        System.out.println(amountAsString);

    }
}
