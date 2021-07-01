package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.SQLFunctions;
import com.techelevator.tenmo.model.TransferSend;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.JdbcTransactionObjectSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionManager;

import java.math.BigDecimal;

@Component
public class JdbcTransferSendDao implements TransferSendDao {
    JdbcTemplate jdbcTemplate;

    public JdbcTransferSendDao(JdbcTemplate jdbcTemplate) {

    }


    @Override
    public TransferSend sendTransferSend(TransferSend sendThisTransfer) {
        TransferSend returnedTransferSend = new TransferSend();

        /* First, check the sender's balance */
        BigDecimal proposedAmountToSend = sendThisTransfer.getAmountToSend();
        int senderId = sendThisTransfer.getFromUserId();

        AuthenticatedAccountBalanceDao authBalanceDao =
                new JdbcAuthenticatedAccountBalanceDao(jdbcTemplate);
        BigDecimal sendersBalance =
                authBalanceDao.getAuthenticatedAccountBalance(senderId).getBalance();
                    // ^^^^ sender's balance

        if (sendersBalance.compareTo(proposedAmountToSend) < 0)
        {
            System.out.println("Throw exception"); /////////////
            return null;
        }

        /* We have enough money to send. Now, we need the new balances. */
        int recipientId = sendThisTransfer.getToUserId();
        BigDecimal recipientBalance =
                authBalanceDao.getAuthenticatedAccountBalance(recipientId).getBalance();
        // ^^^^ recipient's balance

        BigDecimal newRecipientBalance = recipientBalance.add(proposedAmountToSend);
        BigDecimal newSenderBalance = sendersBalance.subtract(proposedAmountToSend);


        /* Now, we're going to get IDs for the updates */
        // Desired table updates:
        // transfer_id | transfer_type_id | transfer_status_id | account_from | account_to | amount (new)
        // ...and, balance updated in accounts table

        // transfer_id is set by the database.

        // we need to grab transfer_type_id (in case it changes later)
        String transferTypeCodeSql = "SELECT transfer_type_id FROM transfer_types " +
                "WHERE transfer_type_desc = 'Send';";
        int transferTypeCode = jdbcTemplate.queryForRowSet(transferTypeCodeSql)
                .getInt("transfer_type_id");

        // we need to grab transfer_status_id (in case it changes later).
        String approvedStatusCodeSql = "SELECT transfer_status_id FROM transfer_statuses " +
                "WHERE transfer_status_desc = 'Approved';";
        int approvedStatusCode = jdbcTemplate.queryForRowSet(approvedStatusCodeSql)
                .getInt("transfer_status_id");

        // we need to grab account_from
        jdbcTemplate.execute(SQLFunctions.createGetUsersAccountIdFunction);
        String accountFromSql = "SELECT pg_temp.getUsersAccount(?) AS account_from_id;";
        int accountFromId = jdbcTemplate.queryForRowSet(accountFromSql)
                .getInt("account_from_id");

        // now we need to grab account_to
        jdbcTemplate.execute(SQLFunctions.createGetUsersAccountIdFunction);
        String accountToSql = "SELECT pg_temp.getUsersAccount(?) AS account_to_id;";
        int accountToId = jdbcTemplate.queryForRowSet(accountToSql)
                .getInt("account_to_id");

        // we know the amount of the transfer. It's proposedAmountToSend

        ///// Now, we just need to use the account information we already have.
        //// accountFromId, accountToId . Then, recipientId or senderId,
        //// then, newRecipientBalance, newSenderBalance

        /////////////////////////////////////////////////////////////
        String sendMoneySql = " BEGIN TRANSACTION; " +
                "INSERT INTO transfer (transfer_type_id, " +
                "transfer_status_id, account_from, account_to, amount) " +
                "VALUES (?,?,?,?,?) " +
                " " +
                "UPDATE accounts " +
                "SET balance = ? WHERE user_id = ?; " +
                "UPDATE accounts " +
                "SET balance = ? WHERE user_id = ?; " +
                "COMMIT TRANSACTION; ";

        TransactionManager tm = new JdbcTransactionObjectSupport();




        return null;
    }
}
