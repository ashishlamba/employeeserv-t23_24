package com.paypal.bfs.test.employeeserv.exceptionhandler;

import com.paypal.bfs.test.employeeserv.bean.Error;
import com.paypal.bfs.test.employeeserv.bean.FieldErrorDetails;
import com.paypal.bfs.test.employeeserv.constants.MessageConstants;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        BindingResult result = ex.getBindingResult();
        return new ResponseEntity<>(processFieldErrors(result.getFieldErrors()), HttpStatus.BAD_REQUEST);
    }

    private Error processFieldErrors(List<FieldError> fieldErrors) {
        List<FieldErrorDetails> errors = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {
            errors.add(FieldErrorDetails.builder().field(fieldError.getField())
                    .message(fieldError.getDefaultMessage()).build());
        }
        return Error.builder().status(HttpStatus.BAD_REQUEST.value())
                .message(MessageConstants.VALIDATION_FAILED).fieldErrors(errors).build();
    }
}
