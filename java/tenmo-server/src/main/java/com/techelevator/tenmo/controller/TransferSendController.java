package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDetailsDao;
import com.techelevator.tenmo.dao.TransferSendDao;
import com.techelevator.tenmo.exception.StandardTenmoException;
import com.techelevator.tenmo.model.TransferDetails;
import com.techelevator.tenmo.model.TransferSend;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class TransferSendController {
    private TransferSendDao transferSendDao;

    public TransferSendController(TransferSendDao transferSendDao) {
        this.transferSendDao = transferSendDao;
    }

    @RequestMapping(path="transfer", method = RequestMethod.POST)
    public TransferSend post(@Valid @RequestBody TransferSend transferSend) throws StandardTenmoException {
        try {
            return transferSendDao.sendTransferSend(transferSend);
        } catch (Exception ex) {
            throw new StandardTenmoException();
        }
    }
}
