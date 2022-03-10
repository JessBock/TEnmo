package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;
import java.security.Principal;

public interface AccountDao {
    public BigDecimal getBalance(long account_id);

    public void transfer(long fromAccountId,long toAccountId, BigDecimal amountTransfer);

    public void viewTransfers(Principal principal);

    public Transfer transactionDetails(long transfer_id);
}
