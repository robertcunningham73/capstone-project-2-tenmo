package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.StandardTenmoException;
import com.techelevator.tenmo.model.TransferDetails;

public interface TransferDetailsDao {
    public TransferDetails getTransferDetails(int transferId) throws StandardTenmoException;
}
