package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;

@RestController
@RequestMapping("/account/")

public class AccountController {
    private AccountDao dao;

    public AccountController(AccountDao dao){
        this.dao = dao;
    }

    @RequestMapping (path = "{account_id}/balance", method = RequestMethod.GET)
    public BigDecimal getBalance(@PathVariable long account_id) {
        return dao.getBalance(account_id);
    }

    @RequestMapping(path = "transfer/{account_from}/{account_to}/{amount}", method = RequestMethod.PUT)
    public void transfer(@PathVariable long account_from,@PathVariable long account_to, @PathVariable BigDecimal amount){
        dao.transfer(account_from, account_to, amount);
    }
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path = "transfers", method = RequestMethod.GET)
    public void getTransfer(Principal principle){
        dao.viewTransfers(principle);
    }

    @RequestMapping(path = "transfer/{transfer_id}", method = RequestMethod.GET)
    public Transfer getTransferDetails(@PathVariable long transfer_id){
        Transfer results = dao.transactionDetails(transfer_id);
        return results;
    }


}
