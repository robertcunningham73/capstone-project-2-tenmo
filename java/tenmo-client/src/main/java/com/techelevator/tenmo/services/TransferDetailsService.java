package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.*;
import com.techelevator.view.ConsoleService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferDetailsService {

    private final String BASE_URL;
    public static String AUTH_TOKEN = "";
    private final RestTemplate restTemplate = new RestTemplate();
    private final ConsoleService console = new ConsoleService(System.in, System.out);

    public TransferDetailsService(String url) {
        BASE_URL = url;
    }

    public TransferDetails getTransferDetails(int transferId) {
        TransferDetails transferDetails = null;
        try {
            transferDetails = restTemplate.exchange(BASE_URL + "transfers/" +
                    transferId, HttpMethod.GET, makeAuthEntity(), TransferDetails.class).getBody();
        } catch (RestClientResponseException ex) {
            System.out.println(ex.getRawStatusCode() + " : " + ex.getStatusText());
        } catch (ResourceAccessException ex) {
            System.out.println(ex.getMessage());
        }
        return transferDetails;
    }


    public void printTransferDetails(int transferId) {
        printDashes();
        System.out.println("Transfer Details");
        printDashes();
        TransferDetails transferDetails = getTransferDetails(transferId);

        if (transferDetails.getTransferId()==0) {
            System.out.println("Sorry, error, transfer not available.");
            return;
        }

        printSpacedOutStrings("Id",String.valueOf(transferDetails.getTransferId()));
        printSpacedOutStrings("From",transferDetails.getFromUserName());
        printSpacedOutStrings("To",transferDetails.getToUserName());
        printSpacedOutStrings("Type",transferDetails.getTransferType());
        printSpacedOutStrings("Status", transferDetails.getTransferStatus());
        printSpacedOutStrings("Amount","$" + transferDetails.getTransferAmount().toString());
    }

    private void printDashes() {
        String dashes = "------------------------";
        System.out.println(dashes);
    }

    private void printSpacedOutStrings(String a, String b) {
        a = a + ":";
        String formatter = "%-10s%10s";
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
