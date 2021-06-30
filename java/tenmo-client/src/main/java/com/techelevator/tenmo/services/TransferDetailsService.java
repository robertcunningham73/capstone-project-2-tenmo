package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedAccountBalance;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.TransferDetails;
import com.techelevator.tenmo.model.User;
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
            /*transferDetails = restTemplate.getForObject(BASE_URL + "transfers/" +
                    transferId, TransferDetails.class);*/
            transferDetails = restTemplate.exchange(BASE_URL + "transfers/" +
                    transferId, HttpMethod.GET, makeAuthEntity(), TransferDetails.class).getBody();
        } catch (RestClientResponseException ex) {
            System.out.println(ex.getRawStatusCode() + " : " + ex.getStatusText());
        } catch (ResourceAccessException ex) {
            System.out.println(ex.getMessage());
        }
        return transferDetails;
    }

    private HttpEntity makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AUTH_TOKEN);
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
    }
}
