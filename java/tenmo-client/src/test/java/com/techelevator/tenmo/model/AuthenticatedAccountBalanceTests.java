package com.techelevator.tenmo.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class AuthenticatedAccountBalanceTests {
    AuthenticatedAccountBalance authenticatedAccountBalanceTest;

    @Before
    public void init()
    {
        authenticatedAccountBalanceTest = new AuthenticatedAccountBalance();

        Assert.assertNotNull(authenticatedAccountBalanceTest);
    }

    @Test
    public void testAuthenticatedAccountBalanceCorrectBalance()
    {
        BigDecimal expected = new BigDecimal("122.01");

        authenticatedAccountBalanceTest.setBalance(new BigDecimal("122.01"));

        BigDecimal result = authenticatedAccountBalanceTest.getBalance();

        Assert.assertEquals(expected,result);

    }

    @Test
    public void testAuthenticatedAccountBalanceString()
    {
        String expected = "Your current account balance is: $7750.20";
        authenticatedAccountBalanceTest.setBalance(new BigDecimal("7750.20"));

        String result = authenticatedAccountBalanceTest.toString();

        Assert.assertEquals(expected,result);
    }



}
