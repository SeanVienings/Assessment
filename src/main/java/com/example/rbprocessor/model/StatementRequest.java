package com.example.rbprocessor.model;

import java.util.ArrayList;
import java.util.List;

public class StatementRequest {

    private List<CustomerStatementRecord> customerStatementRecords = new ArrayList<>();

    public List<CustomerStatementRecord> getCustomerStatementRecords() {
        return customerStatementRecords;
    }

    public void setCustomerStatementRecords(List<CustomerStatementRecord> customerStatementRecords) {
        this.customerStatementRecords = customerStatementRecords;
    }

    public void addCustomerStatementRecords(CustomerStatementRecord customerStatementRecord) {
        this.customerStatementRecords.add(customerStatementRecord);
    }
}
