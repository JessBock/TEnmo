package com.techelevator.tenmo.dao;

import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao {
    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
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

    public void transfer(long fromAccountId,long toAccountId, BigDecimal amountTransfer){
        if (fromAccountId == toAccountId){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Account Numbers Must Be Different!");
        } else if(amountTransfer.compareTo(getBalance(fromAccountId)) > 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not Enough Funds!");
        }else if(amountTransfer.compareTo(BigDecimal.ZERO) < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Must Transfer Positive Amounts!");
        }else{
            String sql = "UPDATE account SET balance = ? WHERE account_id = ?";
            BigDecimal newTotal = getBalance(fromAccountId).subtract(amountTransfer);
            jdbcTemplate.update(sql, newTotal, fromAccountId);
            newTotal = getBalance(toAccountId).add(amountTransfer);
            jdbcTemplate.update(sql,newTotal,toAccountId);

            String sqlStatus = "INSERT INTO transfer (transfer_type_id,transfer_status_id,account_from,account_to,amount) " +
                    "VALUES (2,2,?,?,?)";
            jdbcTemplate.update(sqlStatus,fromAccountId,toAccountId,amountTransfer);
        }

    }
}
