package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.StandardTenmoException;
import com.techelevator.tenmo.model.AuthenticatedAccountBalance;

import java.security.Principal;

public interface AuthenticatedAccountBalanceDao {
    public AuthenticatedAccountBalance getAuthenticatedAccountBalance(Principal principal) throws StandardTenmoException;
}
