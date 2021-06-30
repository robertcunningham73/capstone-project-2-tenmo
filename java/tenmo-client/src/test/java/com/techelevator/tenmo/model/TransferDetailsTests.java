package com.techelevator.tenmo.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class TransferDetailsTests {
    TransferDetails transferDetails;

    @Before
    public void init() {
        transferDetails = new TransferDetails();
        Assert.assertNotNull(transferDetails);
    }

    @Test
    public void testTransferId()
    {
        int expected = 555;
        transferDetails.setTransferId(555);
        int result = transferDetails.getTransferId();

        Assert.assertEquals(expected,result);
    }

    @Test
    public void testToUserName() {
        String expected = "Tach Elevator";
        transferDetails.setToUserName("Tach Elevator");
        String result = transferDetails.getToUserName();
        Assert.assertEquals(expected,result);

    }

    @Test
    public void testFromUserName() {
        String expected = "Tach Elevator";
        transferDetails.setFromUserName("Tach Elevator");
        String result = transferDetails.getFromUserName();
        Assert.assertEquals(expected,result);

    }

    @Test
    public void testSetTransferType() {
        String expected = "Send";
        transferDetails.setTransferType("Send");
        String result = transferDetails.getTransferType();
        Assert.assertEquals(expected,result);
    }

    @Test
    public void testSetTransferStatus() {
        String expected = "Approved";
        transferDetails.setTransferType("Approved");
        String result = transferDetails.getTransferType();
        Assert.assertEquals(expected,result);
    }

    @Test
    public void testSetTransferAmount() {
        BigDecimal expected = new BigDecimal("9999.99");
        transferDetails.setTransferAmount(new BigDecimal("9999.99"));
        BigDecimal result = transferDetails.getTransferAmount();
        Assert.assertEquals(expected,result);
    }

    @Test
    public void testString()
    {
        TransferDetails testTD = new TransferDetails();
        testTD.setTransferAmount(new BigDecimal("999.99"));
        testTD.setTransferType("Send");
        testTD.setToUserName("Bobby");
        testTD.setFromUserName("Joe");
        testTD.setTransferStatus("Approved");
        testTD.setTransferId(5000);
        System.out.println("**** Test print of details");
        System.out.println(testTD);
    }


}
