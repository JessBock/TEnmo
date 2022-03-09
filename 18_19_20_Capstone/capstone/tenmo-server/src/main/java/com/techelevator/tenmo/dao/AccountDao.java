package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

public interface AccountDao {
    public BigDecimal getBalance(long account_id);

    public void transfer(long fromAccountId,long toAccountId, BigDecimal amountTransfer);
}
