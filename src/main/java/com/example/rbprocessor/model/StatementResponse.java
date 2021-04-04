package com.example.rbprocessor.model;

import java.util.ArrayList;
import java.util.List;

public class StatementResponse {

    private String result;
    private List<ExecutedErrorRecord> errorRecords = new ArrayList<>();

    public StatementResponse() {
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<ExecutedErrorRecord> getErrorRecords() {
        return errorRecords;
    }

    public void setErrorRecords(List<ExecutedErrorRecord> errorRecords) {
        this.errorRecords = errorRecords;
    }
}
