package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.security.Principal;

@Component
public class JdbcAccountDao implements AccountDao {
    private JdbcTemplate jdbcTemplate;
    private UserDao userDao;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate, UserDao userDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDao = userDao;
    }

    public BigDecimal getBalance(long id) {
        String sql = "SELECT balance FROM account WHERE account_id = ?";
        BigDecimal balance;

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if (results.next()) {
            balance = results.getBigDecimal("balance");
        } else {
            throw new UsernameNotFoundException("User Not Found");
        }
        return balance;
    }

    public void transfer(long fromAccountId, long toAccountId, BigDecimal amountTransfer) {
        if (fromAccountId == toAccountId) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account Numbers Must Be Different!");
        } else if (amountTransfer.compareTo(getBalance(fromAccountId)) > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not Enough Funds!");
        } else if (amountTransfer.compareTo(BigDecimal.ZERO) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Must Transfer Positive Amounts!");
        } else {
            String sql = "UPDATE account SET balance = ? WHERE account_id = ?";
            BigDecimal newTotal = getBalance(fromAccountId).subtract(amountTransfer);
            jdbcTemplate.update(sql, newTotal, fromAccountId);
            newTotal = getBalance(toAccountId).add(amountTransfer);
            jdbcTemplate.update(sql, newTotal, toAccountId);

            String sqlStatus = "INSERT INTO transfer (transfer_type_id,transfer_status_id,account_from,account_to,amount) " +
                    "VALUES (2,2,?,?,?)";
            jdbcTemplate.update(sqlStatus, fromAccountId, toAccountId, amountTransfer);
        }

    }


    public void viewTransfers(Principal principal) {

        long user = userDao.findIdByUsername(principal.getName());

        String accountSql = "SELECT account_id FROM account WHERE user_id = ?";
        Long results = jdbcTemplate.queryForObject(accountSql,long.class, user);
        System.out.println("Transfer to:\n" + "ID\t\t\t\tFrom/To\t\t\t\t\tAmount\n");
        if (results != null) {
            String sql = "SELECT transfer_id, amount,account_from,account_to " +
                    "FROM transfer " +
                    "WHERE (account_from = ? OR account_to = ?) AND transfer_status_id = 2;";
            SqlRowSet transactions = jdbcTemplate.queryForRowSet(sql,results,results);
            while(transactions.next()){
                long transferId = transactions.getLong("transfer_id");
                BigDecimal amount = transactions.getBigDecimal("amount");
                long account_from = transactions.getLong("account_from");
                long account_to = transactions.getLong("account_to");
                if (results == account_from){
                    String username = userDao.findUsernameByAccountId(account_to);
                    System.out.println(transferId + "\t\t\tTo: " + username + "\t\t\t\t" + "$ " + amount);
                } else if (results == account_to) {
                    String username = userDao.findUsernameByAccountId(account_from);
                    System.out.println(transferId + "\t\t\tFrom: " + username + "\t\t\t" + "$ " + amount);

                }
            }
        }


    }


    public Transfer transactionDetails(long transfer_id){
        String sql = "SELECT transfer_id,account_from,account_to,transfer_type_desc,transfer_status_desc,amount " +
                "FROM transfer " +
                "JOIN transfer_type ON transfer_type.transfer_type_id = transfer.transfer_type_id "+
                "JOIN transfer_status ON transfer_status.transfer_status_id = transfer.transfer_status_id "+
                "WHERE  transfer_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql,transfer_id);
        Transfer transfer =mapRowToTransfer(results);
        return transfer;
    }

    private Transfer mapRowToTransfer(SqlRowSet rowset){
        Transfer transfer = new Transfer();
        transfer.setTransfer_id(rowset.getLong("transfer_id"));
        transfer.setTransfer_type(rowset.getString("transfer_type_desc"));
        transfer.setTransfer_Status(rowset.getString("transfer_status_desc"));
        transfer.setAccount_from(rowset.getLong("account_from"));
        transfer.setAccount_to(rowset.getLong("account_to"));
        transfer.setAmount(rowset.getBigDecimal("amount"));
        return transfer;
    }
}


