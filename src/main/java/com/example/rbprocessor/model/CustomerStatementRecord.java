package com.example.rbprocessor.model;

import java.math.BigDecimal;

public class CustomerStatementRecord {

    private int transactionReference;

    private String accountNumber;

    private BigDecimal startBalance;

    private BigDecimal mutation;

    private String description;

    private BigDecimal endBalance;

    public CustomerStatementRecord() {
    }

    public CustomerStatementRecord(int transactionReference, String accountNumber, BigDecimal startBalance, BigDecimal mutation, String description, BigDecimal endBalance) {
        this.transactionReference = transactionReference;
        this.accountNumber = accountNumber;
        this.startBalance = startBalance;
        this.mutation = mutation;
        this.description = description;
        this.endBalance = endBalance;
    }

    public int getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(int transactionReference) {
        this.transactionReference = transactionReference;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getStartBalance() {
        return startBalance;
    }

    public void setStartBalance(BigDecimal startBalance) {
        this.startBalance = startBalance;
    }

    public BigDecimal getMutation() {
        return mutation;
    }

    public void setMutation(BigDecimal mutation) {
        this.mutation = mutation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getEndBalance() {
        return endBalance;
    }

    public void setEndBalance(BigDecimal endBalance) {
        this.endBalance = endBalance;
    }
}