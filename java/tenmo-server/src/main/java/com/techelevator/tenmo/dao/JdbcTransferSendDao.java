package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.SQLFunctions;
import com.techelevator.tenmo.TenmoApplication;
import com.techelevator.tenmo.exception.StandardTenmoException;
import com.techelevator.tenmo.model.TransferSend;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.JdbcTransactionObjectSupport;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;

@Component
public class JdbcTransferSendDao implements TransferSendDao {
    JdbcTemplate jdbcTemplate;

    public JdbcTransferSendDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    @Transactional(rollbackFor = StandardTenmoException.class)
    public TransferSend sendTransferSend(TransferSend sendThisTransfer, Principal principal)
            throws StandardTenmoException {

        /* First, check the sender's balance */
        BigDecimal proposedAmountToSend = sendThisTransfer.getAmountToSend();
        int senderId = sendThisTransfer.getFromUserId();

        AuthenticatedAccountBalanceDao authBalanceDao =
                new JdbcAuthenticatedAccountBalanceDao(jdbcTemplate);
        BigDecimal sendersBalance =
                authBalanceDao.getAuthenticatedAccountBalance(principal).getBalance();
                    // ^^^^ sender's balance

        if (sendersBalance.compareTo(proposedAmountToSend) < 0)
        {
            throw new StandardTenmoException();
        }

        /* We have enough money to send. Now, we need the new balances. */
        int recipientId = sendThisTransfer.getToUserId();

   //     BigDecimal recipientBalance =
   //             authBalanceDao.getAuthenticatedAccountBalance(recipientId).getBalance();
        // ^^^^ recipient's balance

        jdbcTemplate.execute(SQLFunctions.createGetBalanceGivenUserId);
        String recipientsBalanceSql = "SELECT pg_temp.getBalanceGivenUserId(?) AS recipient_balance;";
        SqlRowSet recipientsBalanceRowSet =
                jdbcTemplate.queryForRowSet(recipientsBalanceSql, recipientId);
        BigDecimal recipientBalance = null;
        if (recipientsBalanceRowSet.next()) {
            recipientBalance = recipientsBalanceRowSet.getBigDecimal("recipient_balance");
        }
        if (recipientBalance==null) { throw new StandardTenmoException();}


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
        SqlRowSet transferTypeCodeRowSet = jdbcTemplate.queryForRowSet(transferTypeCodeSql);
        int transferTypeCode = -1;
        if (transferTypeCodeRowSet.next()) {
            transferTypeCode = transferTypeCodeRowSet.getInt("transfer_type_id");
        }

        // we need to grab transfer_status_id (in case it changes later).
        String approvedStatusCodeSql = "SELECT transfer_status_id FROM transfer_statuses " +
                "WHERE transfer_status_desc = 'Approved';";
        SqlRowSet approvedStatusCodeRowSet = jdbcTemplate.queryForRowSet(approvedStatusCodeSql);
        int approvedStatusCode = -1;
        if (approvedStatusCodeRowSet.next()) {
            approvedStatusCode = approvedStatusCodeRowSet.getInt("transfer_status_id");
        }

        // we need to grab account_from
        jdbcTemplate.execute(SQLFunctions.createGetUsersAccountIdFunction);
        String accountFromIdSql = "SELECT pg_temp.getUsersAccount(?) AS account_from_id;";
        SqlRowSet accountFromIdRowSet = jdbcTemplate.queryForRowSet(accountFromIdSql, senderId);
        int accountFromId = -1;
        if (accountFromIdRowSet.next()) {
            accountFromId = accountFromIdRowSet.getInt("account_from_id");
        }

        // now we need to grab account_to
        jdbcTemplate.execute(SQLFunctions.createGetUsersAccountIdFunction);
        String accountToIdSql = "SELECT pg_temp.getUsersAccount(?) AS account_to_id;";
        SqlRowSet accountToIdRowSet = jdbcTemplate.queryForRowSet(accountToIdSql, recipientId);
        int accountToId = -1;
        if (accountToIdRowSet.next()) {
            accountToId = accountToIdRowSet.getInt("account_to_id");
        }

        if ((accountToId == -1) || (accountFromId == -1) || (approvedStatusCode == -1) || (transferTypeCode == -1)) {
            throw new StandardTenmoException();
        }
        // we know the amount of the transfer. It's proposedAmountToSend

        ///// Now, we just need to use the account information we already have.
        //// accountFromId, accountToId . Then, recipientId or senderId,
        //// then, newRecipientBalance, newSenderBalance

        /////////////////////////////////////////////////////////////
        try {
/*            String addTransferSql = "INSERT INTO transfers (transfer_type_id, " +
                    "transfer_status_id, account_from, account_to, amount) " +
                    "VALUES (?,?,?,?,?);";
            int transferRowUpdated = jdbcTemplate.update(addTransferSql, transferTypeCode, approvedStatusCode,
                    accountFromId, accountToId, proposedAmountToSend);
            if (transferRowUpdated != 1) {
                System.out.println("transferRowUpdated " + transferRowUpdated);
                throw new StandardTenmoException();
            }*/
            String addTransferSql = "INSERT INTO transfers (transfer_type_id, " +
                    "transfer_status_id, account_from, account_to, amount) " +
                    "VALUES (?,?,?,?,?) RETURNING transfer_id;";
            Integer newTransferId = -1;
            newTransferId = jdbcTemplate.queryForObject(addTransferSql,
                    Integer.class, transferTypeCode, approvedStatusCode,
                    accountFromId, accountToId, proposedAmountToSend);
            if (newTransferId == -1) {
                throw new StandardTenmoException();
            }
            sendThisTransfer.setTransferId(newTransferId);


            String updateAccountBalanceSql = "UPDATE accounts SET balance = " +
                    "CASE WHEN user_id = ? THEN ?" + //recipientId, newRecipientBalance
                    "WHEN user_id = ? THEN ? " + //senderId, newSenderBalance
                    "END " +
                    "WHERE user_id IN (?, ?);"; //recipientId, senderId

            int balanceRowsUpdated = jdbcTemplate.update(updateAccountBalanceSql, recipientId, newRecipientBalance,
                    senderId, newSenderBalance, recipientId, senderId);
            if (balanceRowsUpdated != 2) {
                System.out.println("balanceRowsUpdated " + balanceRowsUpdated);
                throw new StandardTenmoException();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new StandardTenmoException();
        }

        return sendThisTransfer;
    }
}
