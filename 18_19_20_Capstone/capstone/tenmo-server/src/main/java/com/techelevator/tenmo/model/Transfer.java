package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {
private long transfer_id;
private String transfer_type;
private String transfer_Status;
private long account_from;
private long account_to;
private BigDecimal amount;

public Transfer(){

}
public Transfer(long transfer_id,String transfer_type,String transfer_Status,long account_from,long account_to,BigDecimal amount){
    this.transfer_id = transfer_id;
    this.transfer_type = transfer_type;
    this.transfer_Status = transfer_Status;
    this.account_from = account_from;
    this.account_to = account_to;
    this.amount = amount;
}

    public long getTransfer_id() {
        return transfer_id;
    }

    public String getTransfer_type(){
        return transfer_type;
    }

    public String getTransfer_Status() {
        return transfer_Status;
    }

    public long getAccount_from() {
        return account_from;
    }

    public long getAccount_to(){
    return account_to;
    }

    public BigDecimal getAmount(){
    return amount;
    }

    public void setTransfer_id(long transfer_id){
    this.transfer_id = transfer_id;
    }

    public void setTransfer_type(String transfer_type){
    this.transfer_type = transfer_type;
    }

    public void setTransfer_Status(String transfer_Status){
    this.transfer_Status = transfer_Status;
    }

    public void setAccount_from(long account_from){
    this.account_from = account_from;
    }

    public void setAccount_to(long account_to){
    this.account_to = account_to;
    }

    public void setAmount(BigDecimal amount){
    this.amount = amount;
    }

}
