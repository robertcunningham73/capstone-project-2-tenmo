package com.techelevator.tenmo;

// SQL Function Shortcuts
// For security, we hard-code these and do not include them in an SQL file
// to be initialized by Spring.
// The scope of pg_temp is connection-specific, ensuring
// permissions and avoiding the need to DROP or CREATE on the main database.
public class SQLFunctions
{
    public static final String createWhoseAccountFunction =
            "DROP FUNCTION IF EXISTS pg_temp.whoseAccount; " +
            "CREATE FUNCTION pg_temp.whoseAccount(integer) RETURNS varchar(50) " +
                    "AS  'SELECT u.username FROM users u JOIN accounts a " +
                    "ON u.user_id = a.user_id WHERE a.account_id=$1;' " +
                    "LANGUAGE SQL IMMUTABLE RETURNS NULL ON NULL INPUT; ";

    public static final String createGetUsersAccountIdFunction =
            "DROP FUNCTION IF EXISTS pg_temp.getUsersAccount; " +
            "CREATE FUNCTION pg_temp.getUsersAccount(integer) RETURNS integer " +
                    "AS 'SELECT account_id FROM accounts WHERE user_id=$1;' " +
                    "LANGUAGE SQL IMMUTABLE RETURNS NULL ON NULL INPUT; ";

    public static final String createGetBalanceGivenUserId =
            "DROP FUNCTION IF EXISTS pg_temp.getBalanceGivenUserId; " +
             "CREATE FUNCTION pg_temp.getBalanceGivenUserId(integer) RETURNS numeric " +
        "AS 'SELECT a.balance AS user_balance FROM accounts a JOIN users u ON " +
        "a.user_id = u.user_id WHERE u.user_id = $1;' " +
            "LANGUAGE SQL IMMUTABLE RETURNS NULL ON NULL INPUT; ";
}
