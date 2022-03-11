package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {
    private long transferId;
    private String transferType;
    private String transferStatus;
    private String usernameFrom;
    private String usernameTo;
    private BigDecimal amount;

    public Transfer() {
    }

    public Transfer(long transferId, String transferType, String transferStatus, String usernameFrom, String usernameTo, BigDecimal amount) {
        this.transferId = transferId;
        this.transferType = transferType;
        this.transferStatus = transferStatus;
        this.usernameFrom = usernameFrom;
        this.usernameTo = usernameTo;
        this.amount = amount;
    }

    public long getTransferId() {
        return transferId;
    }

    public String getTransferType() {
        return transferType;
    }

    public String getTransferStatus() {
        return transferStatus;
    }

    public String getUsernameFrom() {
        return usernameFrom;
    }

    public String getUsernameTo() {
        return usernameTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setTransferId(long transferId) {
        this.transferId = transferId;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
    }

    public void setUsernameFrom(String usernameFrom) {
        this.usernameFrom = usernameFrom;
    }

    public void setUsernameTo(String usernameTo) {
        this.usernameTo = usernameTo;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}
