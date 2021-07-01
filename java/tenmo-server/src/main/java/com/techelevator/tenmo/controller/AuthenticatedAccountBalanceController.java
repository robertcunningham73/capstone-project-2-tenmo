package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AuthenticatedAccountBalanceDao;
import com.techelevator.tenmo.model.AuthenticatedAccountBalance;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
public class AuthenticatedAccountBalanceController {

    private AuthenticatedAccountBalanceDao authenticatedAccountBalanceDao;

    public AuthenticatedAccountBalanceController(AuthenticatedAccountBalanceDao authenticatedAccountBalanceDao) {
        this.authenticatedAccountBalanceDao = authenticatedAccountBalanceDao;
    }

    /*
    * Return a specific balance
    * @return userId and userName for a specific authenticated user
     */
    @RequestMapping(path="userbalance/{id}", method = RequestMethod.GET)
    public AuthenticatedAccountBalance get(@PathVariable int id) {
        return authenticatedAccountBalanceDao.getAuthenticatedAccountBalance(id);
    }
}
