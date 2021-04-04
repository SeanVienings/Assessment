package com.example.rbprocessor.model;

public class ExecutedErrorRecord {
    private int reference;
    private String accountNumber;

    public ExecutedErrorRecord() {
    }

    public int getReference() {
        return reference;
    }

    public void setReference(int reference) {
        this.reference = reference;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
