package com.example.rbprocessor;

import com.example.rbprocessor.controller.CustomerStatementController;
import com.example.rbprocessor.controller.StatementVerificationService;
import com.example.rbprocessor.model.CustomerStatementRecord;
import com.example.rbprocessor.model.ResultStatus;
import com.example.rbprocessor.model.StatementRequest;
import com.example.rbprocessor.model.StatementResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class RbProcessorApplicationTests {

    @Mock
    StatementVerificationService statementVerificationService;

    @InjectMocks
    CustomerStatementController customerStatementController;

    @Test
    void controllerTest() {
        StatementRequest request = new StatementRequest();
        CustomerStatementRecord record = new CustomerStatementRecord(12345, "12345", new BigDecimal("0.00"), new BigDecimal("100.00"), "12345", new BigDecimal("100.00"));
        request.addCustomerStatementRecords(record);

        StatementResponse expectedResponse = new StatementResponse();
        expectedResponse.setResult(ResultStatus.SUCCESSFUL.name());

        when(statementVerificationService.statementValidation(request)).thenReturn(expectedResponse);

        StatementResponse actualResponse = customerStatementController.statementValidation(request);

        Assert.assertEquals(expectedResponse.getResult(), actualResponse.getResult());
    }

    @Test
    void internalServerErrorTest() {
        StatementRequest request = new StatementRequest();
        CustomerStatementRecord record = new CustomerStatementRecord(12345, "12345", null, new BigDecimal("100.00"), "12345", new BigDecimal("100.00"));
        request.addCustomerStatementRecords(record);

        StatementResponse expectedResponse = new StatementResponse();
        expectedResponse.setResult(ResultStatus.INTERNAL_SERVER_ERROR.name());

        when(statementVerificationService.statementValidation(request)).thenReturn(expectedResponse);

        StatementResponse actualResponse = customerStatementController.statementValidation(request);

        Assert.assertEquals(expectedResponse.getResult(), actualResponse.getResult());
    }
}
