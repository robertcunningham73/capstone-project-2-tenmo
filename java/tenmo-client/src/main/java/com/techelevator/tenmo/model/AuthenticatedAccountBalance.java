package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class AuthenticatedAccountBalance {

    private AuthenticatedUser authenticatedUser;
    private BigDecimal balance;

    public AuthenticatedAccountBalance() {};

    public AuthenticatedAccountBalance(AuthenticatedUser user) {
        this.authenticatedUser = user;
    }

    private String balanceToString() {
        String balanceAsString = balance.toString();
        balanceAsString = "$" + balanceAsString;
        return balanceAsString;
    }

    public AuthenticatedUser getAuthenticatedUser() {
        return authenticatedUser;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String toString() {
        String returnString = "Your current account balance is: " + balanceToString();
        return returnString;
    }
}
