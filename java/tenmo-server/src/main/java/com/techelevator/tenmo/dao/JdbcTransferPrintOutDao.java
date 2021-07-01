package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferPrintOut;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferPrintOutDao implements TransferPrintOutDao {
    private JdbcTemplate jdbcTemplate;

    public JdbcTransferPrintOutDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<TransferPrintOut> getAllTransferPrintouts(int id) {
        List<TransferPrintOut> transferPrintOutList = new ArrayList<>();
        TransferPrintOut transferPrintOutToAdd;

        String sql = "BEGIN TRANSACTION; " +
                "DROP FUNCTION IF EXISTS whoseAccount; " +
                "DROP FUNCTION IF EXISTS getUsersAccount; " +

                "CREATE FUNCTION whoseAccount(integer) RETURNS varchar(50) " +
                "AS  'SELECT u.username FROM users u JOIN accounts a " +
                "ON u.user_id = a.user_id WHERE a.account_id=$1;' " +
                "LANGUAGE SQL IMMUTABLE RETURNS NULL ON NULL INPUT; " +

                "CREATE FUNCTION getUsersAccount(integer) RETURNS integer " +
                "AS 'SELECT account_id FROM accounts WHERE user_id=$1;' " +
                "LANGUAGE SQL IMMUTABLE RETURNS NULL ON NULL INPUT; " +

                "SELECT 500 as id_of_transfer, 'From' AS from_to, 'Joe' as person, 500 as amount_of_transfer;"
        + " COMMIT TRANSACTION; ";
                /*
                "SELECT tf.transfer_id as id_of_transfer, 'From:' AS from_to, " +
                "whoseAccount(tf.account_from) AS person, tf.amount AS amount_of_transfer FROM transfers tf " +
                "WHERE tf.account_to=getUsersAccount(?) " +

                "UNION " +

                "SELECT tf.transfer_id as id_of_transfer, 'To:' AS from_to, " +
                "whoseAccount(tf.account_to) AS person, tf.amount AS amount_of_transfer FROM transfers tf " +
                "WHERE tf.account_from=getUsersAccount(?); " +

                "COMMIT TRANSACTION;"; */
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql); //, id, id);
        while (results.next()) {
            transferPrintOutToAdd = mapRowToTransferPrintOut(results);
            transferPrintOutList.add(transferPrintOutToAdd);
        }
        return transferPrintOutList;
    }

    private TransferPrintOut mapRowToTransferPrintOut(SqlRowSet rowSet) {
        TransferPrintOut transferPrintOut = new TransferPrintOut();
        transferPrintOut.setTransferId(rowSet.getInt("id_of_transfer"));
        transferPrintOut.setFromTo(rowSet.getString("from_to"));
        transferPrintOut.setUserName(rowSet.getString("person"));
        transferPrintOut.setAmount(rowSet.getBigDecimal("amount_of_transfer"));
        return transferPrintOut;
    }
}
