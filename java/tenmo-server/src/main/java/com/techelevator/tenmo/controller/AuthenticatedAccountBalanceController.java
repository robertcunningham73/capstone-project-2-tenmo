package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AuthenticatedAccountBalanceDao;
import com.techelevator.tenmo.model.AuthenticatedAccountBalance;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.security.Principal;

@RestController
@PreAuthorize("isAuthenticated()")
public class AuthenticatedAccountBalanceController {

    private AuthenticatedAccountBalanceDao authenticatedAccountBalanceDao;

    public AuthenticatedAccountBalanceController(AuthenticatedAccountBalanceDao authenticatedAccountBalanceDao) {
        this.authenticatedAccountBalanceDao = authenticatedAccountBalanceDao;
    }

    /*
    * Return a specific balance
    * @return userId and userName for a specific authenticated user
     */
    @RequestMapping(path="getbalance", method = RequestMethod.GET)
    public AuthenticatedAccountBalance get(Principal principal) {
        return authenticatedAccountBalanceDao.getAuthenticatedAccountBalance(principal);
    }
}
