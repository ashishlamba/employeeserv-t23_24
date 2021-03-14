package com.paypal.bfs.test.employeeserv.bean;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class Error {

    private int status;
    private String message;
    private List<FieldErrorDetails> fieldErrors;
}
