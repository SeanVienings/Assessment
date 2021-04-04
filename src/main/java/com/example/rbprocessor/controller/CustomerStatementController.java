package com.example.rbprocessor.controller;

import com.example.rbprocessor.model.StatementRequest;
import com.example.rbprocessor.model.StatementResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.example.rbprocessor.model.ResultStatus.BAD_REQUEST;
import static com.example.rbprocessor.model.ResultStatus.INTERNAL_SERVER_ERROR;

@RestController
public class CustomerStatementController {

    final StatementVerificationService statementVerificationService;

    public CustomerStatementController(StatementVerificationService statementVerificationService) {
        this.statementVerificationService = statementVerificationService;
    }

    @PostMapping(value = "api/validate", produces = MediaType.APPLICATION_JSON_VALUE)
    public StatementResponse statementValidation(@RequestBody final StatementRequest request) {
        return statementVerificationService.statementValidation(request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StatementResponse> handleBaseException() {
        final StatementResponse statementResponse = new StatementResponse();
        statementResponse.setResult(INTERNAL_SERVER_ERROR.name());
        return new ResponseEntity<>(statementResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<StatementResponse> handleException() {
        final StatementResponse statementResponse = new StatementResponse();
        statementResponse.setResult(BAD_REQUEST.name());
        return new ResponseEntity<>(statementResponse, HttpStatus.BAD_REQUEST);
    }
}
