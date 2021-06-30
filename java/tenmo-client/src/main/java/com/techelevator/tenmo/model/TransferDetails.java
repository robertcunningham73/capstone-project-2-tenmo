package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class TransferDetails {

    private int transferId;
    private String toUserName;
    private String fromUserName;
    private String transferType;
    private String transferStatus;
    private BigDecimal transferAmount;

    private String amountToString() {return null;}
}
