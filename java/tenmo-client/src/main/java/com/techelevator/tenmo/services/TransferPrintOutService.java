package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.TransferPrintOut;
import com.techelevator.view.ConsoleService;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferPrintOutService {

    private final String BASE_URL;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ConsoleService console = new ConsoleService(System.in, System.out);

    public TransferPrintOutService(String url) {
        BASE_URL = url;
    }

    public TransferPrintOut[] getTransferPrintOutArray() {
        TransferPrintOut[] transferPrintOuts = null;
        try {
            transferPrintOuts = restTemplate.getForObject(BASE_URL + "gettransfers", TransferPrintOut[].class);
        } catch (RestClientResponseException ex) {
            System.out.println(ex.getRawStatusCode() + " : " + ex.getStatusText());
        } catch (ResourceAccessException ex) {
            System.out.println(ex.getMessage());
        }
        return transferPrintOuts;
    }
}
