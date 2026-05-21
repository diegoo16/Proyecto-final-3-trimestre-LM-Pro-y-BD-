package com.Diego.service;

import com.Diego.model.Employee;
import com.Diego.repository.EmployeeRepository;

public class EmployeeService {
    private EmployeeRepository repository =
            new EmployeeRepository();

    public void addEmployee(Employee e){
        repository.create(e);
    }
}