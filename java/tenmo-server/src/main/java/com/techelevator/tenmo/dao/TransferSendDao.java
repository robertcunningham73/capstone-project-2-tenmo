package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.StandardTenmoException;
import com.techelevator.tenmo.model.TransferSend;

public interface TransferSendDao {
    TransferSend sendTransferSend(TransferSend sendThisTransfer) throws StandardTenmoException;
}
