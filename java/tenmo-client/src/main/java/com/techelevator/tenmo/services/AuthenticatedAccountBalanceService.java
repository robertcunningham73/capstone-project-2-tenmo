package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedAccountBalance;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import com.techelevator.view.ConsoleService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class AuthenticatedAccountBalanceService {

    private final String BASE_URL;
    public static String AUTH_TOKEN = "";
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
            /*userBalance = restTemplate.getForObject(BASE_URL + "userbalance/" +
                    user.getId(), AuthenticatedAccountBalance.class);*/
            userBalance = restTemplate.exchange(BASE_URL + "userbalance/" +
                    user.getId(), HttpMethod.GET, makeAuthEntity(), AuthenticatedAccountBalance.class).getBody();
        } catch (RestClientResponseException ex) {
            System.out.println(ex.getRawStatusCode() + " : " + ex.getStatusText());
        } catch (ResourceAccessException ex) {
            System.out.println(ex.getMessage());
        }
        return userBalance;
    }

    private HttpEntity makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AUTH_TOKEN);
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
    }
}
