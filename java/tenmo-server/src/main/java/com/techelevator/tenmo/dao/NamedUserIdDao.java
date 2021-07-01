package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.NamedUserId;

import java.util.List;

public interface NamedUserIdDao {
    List<NamedUserId> getAllUsers();
}
