package com.paypal.bfs.test.employeeserv.mapper;


import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.entity.EmployeeEntity;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    /**
     * Maps employee to employee entity object for db operations.
     *
     * @param employee employee object.
     * @return employee entity object.
     */
    public EmployeeEntity forDB(Employee employee) {
        return EmployeeEntity.builder()
                .first_name(employee.getFirstName())
                .last_name(employee.getLastName())
                .date_of_birth(employee.getDateOfBirth())
                .address_line1(employee.getAddressLine1())
                .address_line2(employee.getAddressLine2())
                .city(employee.getCity())
                .state(employee.getState())
                .zip_code(employee.getZipCode())
                .country(employee.getCountry())
                .build();
    }

    /**
     * Maps employee entity object to employee object for API response.
     *
     * @param employeeEntity employee entity object.
     * @return employee object.
     */
    public Employee forAPI(EmployeeEntity employeeEntity) {
        Employee employee = new Employee();
        employee.setId(Integer.valueOf(employeeEntity.getId()));
        employee.setFirstName(employeeEntity.getFirst_name());
        employee.setLastName(employeeEntity.getLast_name());
        employee.setDateOfBirth(employeeEntity.getDate_of_birth());
        employee.setAddressLine1(employeeEntity.getAddress_line1());
        employee.setAddressLine2(employeeEntity.getAddress_line2());
        employee.setCity(employeeEntity.getCity());
        employee.setState(employeeEntity.getState());
        employee.setZipCode(employeeEntity.getZip_code());
        employee.setCountry(employeeEntity.getCountry());
        return employee;
    }

}
