package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferPrintOutDao;
import com.techelevator.tenmo.model.TransferPrintOut;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransferPrintOutController {

    private TransferPrintOutDao transferPrintOutDao;

    public TransferPrintOutController(TransferPrintOutDao transferPrintOutDao) {
        this.transferPrintOutDao = transferPrintOutDao;
    }

    /*
    * Return transfer id, amount, and to/from users from the perspective
    *    of a specific user
    * @return a list of transfer printouts
    */
    @RequestMapping(path="gettransfers/{id}", method = RequestMethod.GET)
    public List<TransferPrintOut> get(@PathVariable int id) {
        return transferPrintOutDao.getAllTransferPrintouts(id);
    }
}
