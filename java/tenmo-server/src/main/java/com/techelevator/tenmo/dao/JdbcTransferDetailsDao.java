package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.SQLFunctions;
import com.techelevator.tenmo.model.NamedUserId;
import com.techelevator.tenmo.model.TransferDetails;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcTransferDetailsDao implements TransferDetailsDao {
    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDetailsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public TransferDetails getTransferDetails(int transferId) {
        TransferDetails transferDetails = new TransferDetails();
        jdbcTemplate.execute(SQLFunctions.createWhoseAccountFunction);
        String sql = "SELECT tf.transfer_id as id_of_transfer, " +
                "whoseAccount(tf.account_from) as from_account, " +
                "whoseAccount(tf.account_to) AS to_account, " +
                "tt.transfer_type_desc AS type_description, " +
                "ts.transfer_status_desc AS status_description, " +
                "tf.amount AS transfer_amount " +
                "FROM transfers tf " +
                "JOIN transfer_types tt " +
                "ON tf.transfer_type_id = tt.transfer_type_id " +
                "JOIN transfer_statuses ts ON tf.transfer_status_id = ts.transfer_status_id " +
                "WHERE tf.transfer_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        if (results.next()) {
            transferDetails = mapRowToTransferDetails(results);
        }
        return transferDetails;
    }

    private TransferDetails mapRowToTransferDetails(SqlRowSet rowSet)
    {
        TransferDetails transferDetails = new TransferDetails();
        transferDetails.setTransferId(rowSet.getInt("id_of_transfer"));
        transferDetails.setToUserName(rowSet.getString("to_account"));
        transferDetails.setFromUserName(rowSet.getString("from_account"));
        transferDetails.setTransferType(rowSet.getString("type_description"));
        transferDetails.setTransferStatus(rowSet.getString("status_description"));
        transferDetails.setTransferAmount(rowSet.getBigDecimal("transfer_amount"));
        return transferDetails;
    }

}


