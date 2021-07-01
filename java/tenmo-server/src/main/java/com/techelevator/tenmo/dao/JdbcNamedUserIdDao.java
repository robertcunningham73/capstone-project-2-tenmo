package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.NamedUserId;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcNamedUserIdDao implements NamedUserIdDao {
    private JdbcTemplate jdbcTemplate;


    public JdbcNamedUserIdDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<NamedUserId> getAllUsers() {
        List<NamedUserId> userIdList = new ArrayList<>();
        NamedUserId namedUserToAdd;

        String sql = "SELECT user_id, username FROM users;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            namedUserToAdd = mapRowToNamedUserId(results);
            userIdList.add(namedUserToAdd);
        }
        return userIdList;

    }

    private NamedUserId mapRowToNamedUserId(SqlRowSet rowSet)
    {
        NamedUserId namedUserId = new NamedUserId();
        namedUserId.setUserId(rowSet.getInt("user_id"));
        namedUserId.setUserName(rowSet.getString("username"));
        return namedUserId;
    }
}
