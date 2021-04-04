package com.example.rbprocessor.controller;

import com.example.rbprocessor.model.CustomerStatementRecord;
import com.example.rbprocessor.model.ResultStatus;
import com.example.rbprocessor.model.StatementRequest;
import com.example.rbprocessor.model.StatementResponse;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

class CustomerStatementControllerTest {

    StatementVerificationService service = new StatementVerificationService();

    StatementRequest request;
    CustomerStatementRecord record;

    @BeforeEach
    void setUp() {
        request = new StatementRequest();
        record = new CustomerStatementRecord(12345, "12345", new BigDecimal("0.00"), new BigDecimal("100.00"), "12345", new BigDecimal("100.00"));
        request.addCustomerStatementRecords(record);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void statementSuccessfulValidation() {
        List<CustomerStatementRecord> customerStatementRecordList = request.getCustomerStatementRecords();
        Assert.assertNotNull(customerStatementRecordList);

        final StatementResponse response = service.statementValidation(request);
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getResult(), ResultStatus.SUCCESSFUL.name());
    }

    @Test
    void multipleSuccessfulValidation() {
        List<CustomerStatementRecord> customerStatementRecordList = request.getCustomerStatementRecords();
        Assert.assertNotNull(customerStatementRecordList);
        final CustomerStatementRecord record2 = new CustomerStatementRecord(32487, "32487", new BigDecimal("0.00"), new BigDecimal("100.00"), "32487", new BigDecimal("100.00"));
        final CustomerStatementRecord record3 = new CustomerStatementRecord(52341, "52341", new BigDecimal("0.00"), new BigDecimal("100.00"), "52341", new BigDecimal("100.00"));
        final CustomerStatementRecord record4 = new CustomerStatementRecord(64978, "64978", new BigDecimal("0.00"), new BigDecimal("100.00"), "64978", new BigDecimal("100.00"));
        final CustomerStatementRecord record5 = new CustomerStatementRecord(89457, "89457", new BigDecimal("0.00"), new BigDecimal("100.00"), "89457", new BigDecimal("100.00"));

        request.addCustomerStatementRecords(record2);
        request.addCustomerStatementRecords(record3);
        request.addCustomerStatementRecords(record4);
        request.addCustomerStatementRecords(record5);

        final StatementResponse response = service.statementValidation(request);
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getResult(), ResultStatus.SUCCESSFUL.name());
    }

    @Test
    void duplicateFailValidation() {
        List<CustomerStatementRecord> customerStatementRecordList = request.getCustomerStatementRecords();
        Assert.assertNotNull(customerStatementRecordList);
        final CustomerStatementRecord record2 = new CustomerStatementRecord(32487, "32487", new BigDecimal("0.00"), new BigDecimal("100.00"), "32487", new BigDecimal("100.00"));
        final CustomerStatementRecord record3 = new CustomerStatementRecord(32487, "32487", new BigDecimal("0.00"), new BigDecimal("100.00"), "32487", new BigDecimal("100.00"));
        request.addCustomerStatementRecords(record2);
        request.addCustomerStatementRecords(record3);

        final StatementResponse response = service.statementValidation(request);
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getResult(), ResultStatus.DUPLICATE_REFERENCE.name());
        Assert.assertEquals(1, response.getErrorRecords().size());
        Assert.assertEquals("32487", response.getErrorRecords().iterator().next().getAccountNumber());
    }

    @Test
    void incorrectEndBalanceValidation() {
        List<CustomerStatementRecord> customerStatementRecordList = request.getCustomerStatementRecords();
        Assert.assertNotNull(customerStatementRecordList);
        record.setEndBalance(new BigDecimal("50"));

        final StatementResponse response = service.statementValidation(request);
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getResult(), ResultStatus.INCORRECT_END_BALANCE.name());
        Assert.assertEquals(1, response.getErrorRecords().size());
        Assert.assertEquals("12345", response.getErrorRecords().iterator().next().getAccountNumber());
    }

    @Test
    void duplicateAndIncorrectFailValidation() {
        List<CustomerStatementRecord> customerStatementRecordList = request.getCustomerStatementRecords();
        Assert.assertNotNull(customerStatementRecordList);
        final CustomerStatementRecord record2 = new CustomerStatementRecord(32487, "32487", new BigDecimal("0.00"), new BigDecimal("100.00"), "32487", new BigDecimal("100.00"));
        final CustomerStatementRecord record3 = new CustomerStatementRecord(32487, "32487", new BigDecimal("0.00"), new BigDecimal("100.00"), "32487", new BigDecimal("500.00"));
        request.addCustomerStatementRecords(record2);
        request.addCustomerStatementRecords(record3);

        final StatementResponse response = service.statementValidation(request);
        Assert.assertNotNull(response);
        Assert.assertEquals(ResultStatus.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE.name(), response.getResult());
        Assert.assertEquals(2, response.getErrorRecords().size());
        Assert.assertEquals("32487", response.getErrorRecords().iterator().next().getAccountNumber());
    }
}