package com.paypal.bfs.test.employeeserv.bean;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FieldErrorDetails {
    private String field;
    private String message;
}
