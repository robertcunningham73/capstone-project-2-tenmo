package com.techelevator.tenmo.model;

import java.math.BigDecimal;
// import java.lang.StringBuilder;

public class TransferPrintOut {

    private int transferId;
    private String fromTo;
    private String userName;
    private BigDecimal amount;

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public String getFromTo() {
        return fromTo;
    }

    public void setFromTo(String fromTo) {
        this.fromTo = fromTo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getAmountAsString() {
//        StringBuilder sB = new StringBuilder();
        String sBformat = "$%8s";
        String returnString = String.format(sBformat,getAmount().toString());
        return returnString;


    }
}
