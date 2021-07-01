package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class TransferDetails {

    private int transferId;
    private String toUserName;
    private String fromUserName;
    private String transferType;
    private String transferStatus;
    private BigDecimal transferAmount;

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public String getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    private String amountToString() {
        String returnString = transferAmount.toString();
        returnString = "$" + returnString;
        return returnString;
    }

    public String toString() {
        String returnString = "";
        returnString += "Id: " + getTransferId() + System.lineSeparator();
        returnString += "From: " + getFromUserName() + System.lineSeparator();
        returnString += "To: " + getToUserName() + System.lineSeparator();
        returnString += "Type: " + getTransferType() + System.lineSeparator();
        returnString += "Status: " + getTransferStatus() + System.lineSeparator();
        returnString += "Amount: " + amountToString();
        return returnString;
    }
}
