package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.NamedUserIdDao;
import com.techelevator.tenmo.model.NamedUserId;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NamedUserIdController
{
    private NamedUserIdDao namedUserIdDao;

    public NamedUserIdController(NamedUserIdDao namedUserIdDao) {

        this.namedUserIdDao = namedUserIdDao;
    }

    /*
     * Return a specific balance
     * @return userId and userName for a specific authenticated user
     */
    @RequestMapping(path="listusers", method = RequestMethod.GET)
    public List<NamedUserId> get() {
        return namedUserIdDao.getAllUsers();
    }
}
