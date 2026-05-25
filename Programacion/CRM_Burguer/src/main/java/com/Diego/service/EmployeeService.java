package com.Diego.service;

import com.Diego.model.Employee;
import com.Diego.repository.EmployeeRepository;
import java.util.List;

public class EmployeeService {

    private EmployeeRepository repository = new EmployeeRepository();

    public void addEmployee(Employee e) {
        repository.create(e);
    }

    public Employee getEmployeeById(int id) {
        return repository.findById(id);
    }

    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    public void updateEmployee(Employee e) {
        repository.update(e);
    }

    public void deleteEmployee(int id) {
        repository.delete(id);
    }
}