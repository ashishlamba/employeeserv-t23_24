package com.paypal.bfs.test.employeeserv.impl;

import com.paypal.bfs.test.employeeserv.api.EmployeeResource;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.bean.Error;
import com.paypal.bfs.test.employeeserv.constants.MessageConstants;
import com.paypal.bfs.test.employeeserv.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Optional;

/**
 * Implementation class for employee resource.
 */
@RestController
public class EmployeeResourceImpl implements EmployeeResource {

    @Autowired
    private final EmployeeService employeeService;

    /**
     * EmployeeResourceImpl constructor.
     *
     * @param employeeService employeeService object.
     */
    private EmployeeResourceImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Get employee by Id.
     *
     * @param id employee id.
     * @return employee details
     */
    @Override
    public ResponseEntity<Employee> employeeGetById(String id) {

        Optional<Employee> employee = employeeService.byId(id);
        return employee.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Creates employee if not exists.
     *
     * @param employeeRequest employee request object.
     * @return employee details on success or error details on error.
     */
    @Override
    public ResponseEntity<Object> createEmployee(Employee employeeRequest) {
        if (Objects.nonNull(employeeRequest.getId())) {
            Optional<Employee> employee = employeeService.byId(String.valueOf(employeeRequest.getId()));
            if (employee.isPresent()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Error.builder().status(HttpStatus.FORBIDDEN.value()).message(MessageConstants.EMP_ALREADY_EXISTS).build());
            }
        }
        return new ResponseEntity<>(employeeService.create(employeeRequest), HttpStatus.CREATED);
    }
}
