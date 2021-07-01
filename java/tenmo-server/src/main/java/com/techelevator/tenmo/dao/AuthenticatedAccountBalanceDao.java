package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.AuthenticatedAccountBalance;

public interface AuthenticatedAccountBalanceDao {
    public AuthenticatedAccountBalance getAuthenticatedAccountBalance(int userId);
}
