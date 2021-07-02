package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.NamedUserId;
import com.techelevator.view.ConsoleService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class NamedUserIdService {

    private final String BASE_URL;
    public static String AUTH_TOKEN = "";
    private final RestTemplate restTemplate = new RestTemplate();
    private final ConsoleService console = new ConsoleService(System.in, System.out);

    public NamedUserIdService(String url) {
        BASE_URL = url;
    }

    public NamedUserId[] getNamedUserIdArray() {
        NamedUserId[] namedUserIds = null;
        try {
            /*namedUserIds = restTemplate.getForObject(BASE_URL + "listusers",
                    NamedUserId[].class);*/
            namedUserIds = restTemplate.exchange(BASE_URL + "listusers", HttpMethod.GET, makeAuthEntity(),
                    NamedUserId[].class).getBody();
        } catch (RestClientResponseException ex) {
            System.out.println(ex.getRawStatusCode() + " : " + ex.getStatusText());
        } catch (ResourceAccessException ex) {
            System.out.println(ex.getMessage());
        }
        return namedUserIds;
    }

    public void printUserIds() {
        printDashes();
        printSpacedOutStrings("User's ID", "User's Name");
        printDashes();
        NamedUserId[] namedUserIds = getNamedUserIdArray();
        for (NamedUserId namedUserId : namedUserIds) {
            printSpacedOutStrings(String.valueOf(namedUserId.getUserId()), namedUserId.getUserName());
        }
    }

    private void printDashes() {
        String dashes = "-----------------------------------------";
        System.out.println(dashes);
    }

    private void printSpacedOutStrings(String a, String b) {
        String formatter = "%-20s%-20s";
        String printString = String.format(formatter, a, b);
        System.out.println(printString);
    }

    private HttpEntity makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AUTH_TOKEN);
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
    }
}
