package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferPrintOut;

import java.util.List;

public interface TransferPrintOutDao {
    List<TransferPrintOut> getAllTransferPrintouts(int id);
}
