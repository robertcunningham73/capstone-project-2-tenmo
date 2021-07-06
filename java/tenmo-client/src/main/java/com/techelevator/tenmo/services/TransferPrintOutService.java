package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.NamedUserId;
import com.techelevator.tenmo.model.TransferPrintOut;
import com.techelevator.view.ConsoleService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferPrintOutService {

    private final String BASE_URL;
    public static String AUTH_TOKEN = "";
    private final RestTemplate restTemplate = new RestTemplate();
    private final ConsoleService console = new ConsoleService(System.in, System.out);

    public TransferPrintOutService(String url) {
        BASE_URL = url;
    }

    public TransferPrintOut[] getTransferPrintOutArray(int userId) {
        TransferPrintOut[] transferPrintOuts = null;
        try {
//            transferPrintOuts = restTemplate.getForObject(BASE_URL + "gettransfers", TransferPrintOut[].class);
            transferPrintOuts = restTemplate.exchange(BASE_URL + "gettransfers/" + userId,
                    HttpMethod.GET, makeAuthEntity(), TransferPrintOut[].class).getBody();
        } catch (RestClientResponseException ex) {
            System.out.println(ex.getRawStatusCode() + " : " + ex.getStatusText());
        } catch (ResourceAccessException ex) {
            System.out.println(ex.getMessage());
        }
        return transferPrintOuts;
    }


    public int printTransferPrintOuts(int userId) {
        TransferPrintOut[] transferPrintOuts = getTransferPrintOutArray(userId);
        if (transferPrintOuts == null || transferPrintOuts.length==0) {
            System.out.println("Sorry, no transfers.");
            return -1;
        }
        printDashes();
        printSpacedOutStrings("Transfer's ID", "From/To",
                "","Amount");
        printDashes();
        for (TransferPrintOut transferPrintOut : transferPrintOuts) {
            String transferIdString = String.valueOf(transferPrintOut.getTransferId());
            printSpacedOutStrings(transferIdString, transferPrintOut.getFromTo(),
                    transferPrintOut.getUserName(), transferPrintOut.getAmountAsString());
        }
        printDashes();
        return 0;
    }

    private void printDashes() {
        String dashes = "------------------------------------------------------------";
        System.out.println(dashes);
    }

    private void printSpacedOutStrings(String a, String b, String c, String d) {
        String formatter = "%-20s%-6s%-25s%-10s";
        String printString = String.format(formatter, a, b, c, d);
        System.out.println(printString);
    }







    private HttpEntity makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AUTH_TOKEN);
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
    }
}
