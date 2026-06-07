package com.Diego.service;

import com.Diego.model.Employee;
import com.Diego.repository.EmployeeRepository;
import java.util.List;

public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService() {
        this.repository = new EmployeeRepository();
    }

    public void addEmployee(Employee e) {
        if (e == null || e.getName() == null || e.getName().trim().isEmpty()) {
            System.out.println(" Error: El nombre del empleado no puede estar vacío.");
            return;
        }
        if (e.getSalary() <= 0) {
            System.out.println(" Error: El salario debe ser mayor que 0.");
            return;
        }
        repository.create(e);
    }

    public Employee getEmployeeById(int id) {
        return repository.findById(id);
    }

    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    public void updateEmployee(Employee e) {
        if (e == null || e.getId() <= 0) {
            System.out.println(" Error: Datos inválidos para actualizar.");
            return;
        }
        repository.update(e);
    }

    public void deleteEmployee(int id) {
        try {
            repository.delete(id);
            System.out.println(" Empleado eliminado correctamente.");
        } catch (Exception e) {
            System.out.println(" Error al eliminar empleado: " + e.getMessage());
        }
    }
}