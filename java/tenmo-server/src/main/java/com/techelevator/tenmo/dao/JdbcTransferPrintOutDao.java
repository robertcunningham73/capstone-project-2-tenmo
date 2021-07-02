package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.SQLFunctions;
import com.techelevator.tenmo.exception.StandardTenmoException;
import com.techelevator.tenmo.model.TransferPrintOut;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("isAuthenticated()")
    public List<TransferPrintOut> getAllTransferPrintouts(int id) throws StandardTenmoException {
        try {
            List<TransferPrintOut> transferPrintOutList = new ArrayList<>();
            TransferPrintOut transferPrintOutToAdd;

            jdbcTemplate.execute(SQLFunctions.createGetUsersAccountIdFunction); // pg_temp scope, temporary
            jdbcTemplate.execute(SQLFunctions.createWhoseAccountFunction);
            String sqlGetTransferInfo =
                    "SELECT tf.transfer_id as id_of_transfer, 'From:' AS from_to, " +
                            "whoseAccount(tf.account_from) AS person, tf.amount AS amount_of_transfer FROM transfers tf " +
                            "WHERE tf.account_to=pg_temp.getUsersAccount(?) " +

                            "UNION " +

                            "SELECT tf.transfer_id as id_of_transfer, 'To:' AS from_to, " +
                            "whoseAccount(tf.account_to) AS person, tf.amount AS amount_of_transfer FROM transfers tf " +
                            "WHERE tf.account_from=pg_temp.getUsersAccount(?) ORDER BY id_of_transfer ASC; ";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetTransferInfo, id, id); //, id, id);
            while (results.next()) {
                transferPrintOutToAdd = mapRowToTransferPrintOut(results);
                transferPrintOutList.add(transferPrintOutToAdd);
            }
            return transferPrintOutList;
        } catch (Exception ex) { throw new StandardTenmoException(); }
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
