package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.AuthenticatedAccountBalance;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;

@Component
// TODO should implement some exceptions
public class JdbcAuthenticatedAccountBalanceDao implements AuthenticatedAccountBalanceDao {

    private JdbcTemplate jdbcTemplate;
    //private AuthenticatedAccountBalanceDao authenticatedAccountBalanceDao;

    public JdbcAuthenticatedAccountBalanceDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

   /* public JdbcAuthenticatedAccountBalanceDao(AuthenticatedAccountBalanceDao authenticatedAccountBalanceDao) {
        this.authenticatedAccountBalanceDao = authenticatedAccountBalanceDao;
       // this.jdbcTemplate = new JdbcTemplate(dataSource);
    }*/

    @Override
    public AuthenticatedAccountBalance getAuthenticatedAccountBalance(int userId) {
        AuthenticatedAccountBalance authenticatedAccountBalance = null;
        String sql = "SELECT a.balance AS user_balance FROM accounts a JOIN users u ON " +
                "a.user_id = u.user_id WHERE a.user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        if (results.next()) {
            authenticatedAccountBalance = mapRowToAuthenticatedAccountBalance(results);
        }
        return authenticatedAccountBalance;
    }

    private AuthenticatedAccountBalance mapRowToAuthenticatedAccountBalance(SqlRowSet rowset) {
        AuthenticatedAccountBalance authenticatedAccountBalance = new AuthenticatedAccountBalance();
        authenticatedAccountBalance.setBalance(rowset.getBigDecimal("user_balance"));
        return authenticatedAccountBalance;
    }
}
