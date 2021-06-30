package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedAccountBalance;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import com.techelevator.view.ConsoleService;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class AuthenticatedAccountBalanceService {

    private final String BASE_URL;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ConsoleService console = new ConsoleService(System.in, System.out);

    public AuthenticatedAccountBalanceService(String url) {
        BASE_URL = url;
    }

    public AuthenticatedAccountBalance getBalance(AuthenticatedUser authUser) {
        User user = authUser.getUser();
        if (user == null) { return null; }
        AuthenticatedAccountBalance userBalance = null;
        try {
            userBalance = restTemplate.getForObject(BASE_URL + "userbalance/" +
                    user.getId(), AuthenticatedAccountBalance.class);
        } catch (RestClientResponseException ex) {
            System.out.println(ex.getRawStatusCode() + " : " + ex.getStatusText());
        } catch (ResourceAccessException ex) {
            System.out.println(ex.getMessage());
        }
        return userBalance;
    }
}
