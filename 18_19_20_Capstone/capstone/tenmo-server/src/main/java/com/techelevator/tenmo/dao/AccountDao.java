package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

public interface AccountDao {
    public BigDecimal getBalance(long account_id);

    public void transfer(long fromAccountId,long toAccountId, BigDecimal amountTransfer);

    public List<Transfer> viewTransfers(Principal principal);

    public Transfer transactionDetails(long transferId);
}
