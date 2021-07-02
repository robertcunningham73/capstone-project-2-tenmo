package com.techelevator.tenmo.model;

import java.math.BigDecimal;
import javax.validation.constraints.*;


public class TransferSend {

    private int transferId;
    @NotNull( message = "toUserId is required")
    private int toUserId;
    @NotNull( message = "fromUserId is required")
    private int fromUserId;
    @Positive( message = "amount to send must be greater than 0")
    private BigDecimal amountToSend;

    public TransferSend()
    {

    }

    public TransferSend(int fromUserId, int toUserId, BigDecimal amountToSend)
    {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.amountToSend = amountToSend;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }

    public int getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(int fromUserId) {
        this.fromUserId = fromUserId;
    }

    public BigDecimal getAmountToSend() {
        return amountToSend;
    }

    public void setAmountToSend(BigDecimal amountToSend) {
        this.amountToSend = amountToSend;
    }
}
