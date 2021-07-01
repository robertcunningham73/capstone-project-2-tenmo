package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class AuthenticatedAccountBalance {

    private BigDecimal balance;

    public AuthenticatedAccountBalance() {};

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

}
