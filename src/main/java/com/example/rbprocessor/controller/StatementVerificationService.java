package com.example.rbprocessor.controller;

import com.example.rbprocessor.model.CustomerStatementRecord;
import com.example.rbprocessor.model.ExecutedErrorRecord;
import com.example.rbprocessor.model.StatementRequest;
import com.example.rbprocessor.model.StatementResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

import static com.example.rbprocessor.model.ResultStatus.DUPLICATE_REFERENCE;
import static com.example.rbprocessor.model.ResultStatus.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE;
import static com.example.rbprocessor.model.ResultStatus.INCORRECT_END_BALANCE;
import static com.example.rbprocessor.model.ResultStatus.SUCCESSFUL;

@Service
public class StatementVerificationService {

    public StatementResponse statementValidation(final StatementRequest request) {
        final StatementResponse statementResponse = new StatementResponse();

        final List<CustomerStatementRecord> customerStatementRecordList = request.getCustomerStatementRecords();
        return validation(customerStatementRecordList, statementResponse);
    }

    private StatementResponse validation(final List<CustomerStatementRecord> customerStatementRecordList, final StatementResponse response) {
        final HashMap<Integer, CustomerStatementRecord> customerStatementRecordMap = new HashMap<>();
        final StatementResponse statementResponse = new StatementResponse();
        customerStatementRecordList.forEach(customerStatementRecord -> {
            if (!customerStatementRecordMap.containsKey(customerStatementRecord.getTransactionReference())) {
                if (!validateBalance(customerStatementRecord)) {
                    statementResponse.setResult(INCORRECT_END_BALANCE.name());
                    setErrorRecord(statementResponse, customerStatementRecord);
                }
                customerStatementRecordMap.put(customerStatementRecord.getTransactionReference(), customerStatementRecord);
            } else {
                statementResponse.setResult(DUPLICATE_REFERENCE.name());
                setErrorRecord(statementResponse, customerStatementRecord);
                if (!validateBalance(customerStatementRecord)) {
                    statementResponse.setResult(DUPLICATE_REFERENCE_INCORRECT_END_BALANCE.name());
                    setErrorRecord(statementResponse, customerStatementRecord);
                }
            }
        });
        if (statementResponse.getErrorRecords().isEmpty()) {
            statementResponse.setResult(SUCCESSFUL.name());
        }
        return statementResponse;
    }

    private void setErrorRecord(StatementResponse statementResponse, CustomerStatementRecord customerStatementRecord) {
        final ExecutedErrorRecord errorRecord = new ExecutedErrorRecord();
        errorRecord.setReference(customerStatementRecord.getTransactionReference());
        errorRecord.setAccountNumber(customerStatementRecord.getAccountNumber());
        statementResponse.getErrorRecords().add(errorRecord);
    }

    private Boolean validateBalance(final CustomerStatementRecord record) {
        return record.getStartBalance().add(record.getMutation()).equals(record.getEndBalance());
    }
}
