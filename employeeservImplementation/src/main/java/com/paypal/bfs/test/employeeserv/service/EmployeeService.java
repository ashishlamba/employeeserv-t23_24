package com.paypal.bfs.test.employeeserv.service;

import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.entity.EmployeeEntity;
import com.paypal.bfs.test.employeeserv.mapper.EmployeeMapper;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private final EmployeeRepository employeeRepository;

    @Autowired
    private final EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    /**
     * Get employee by id.
     *
     * @param id
     * @return employee details.
     */
    public Optional<Employee> byId(String id) {
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(Integer.valueOf(id));
        if (employeeEntity.isPresent()) {
            Employee e = employeeMapper.forAPI(employeeEntity.get());
            if (Objects.nonNull(e)) {
                return Optional.of(e);
            }
        }
        return Optional.empty();
    }

    /**
     * Creates new employee if not exists.
     *
     * @param employeeRequest employee request object.
     * @return employee details.
     */
    public Employee create(Employee employeeRequest) {
        EmployeeEntity employeeEntity = employeeRepository.save(employeeMapper.forDB(employeeRequest));
        return employeeMapper.forAPI(employeeEntity);
    }

}
