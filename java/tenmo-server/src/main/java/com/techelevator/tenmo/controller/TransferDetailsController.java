package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDetailsDao;
import com.techelevator.tenmo.dao.TransferPrintOutDao;
import com.techelevator.tenmo.exception.StandardTenmoException;
import com.techelevator.tenmo.model.TransferDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
public class TransferDetailsController {
    private TransferDetailsDao transferDetailsDao;

    public TransferDetailsController(TransferDetailsDao transferDetailsDao) {
        this.transferDetailsDao = transferDetailsDao;
    }

    @RequestMapping(path="transfers/{id}", method = RequestMethod.GET)
    public TransferDetails get(@PathVariable int id) throws StandardTenmoException {
        try {
            return transferDetailsDao.getTransferDetails(id);
        } catch (Exception ex) { throw new StandardTenmoException(); }
    }

}
