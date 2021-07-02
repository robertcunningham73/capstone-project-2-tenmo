package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.StandardTenmoException;
import com.techelevator.tenmo.model.TransferSend;

import java.security.Principal;

public interface TransferSendDao {
    TransferSend sendTransferSend(TransferSend sendThisTransfer, Principal principal) throws StandardTenmoException;
}
