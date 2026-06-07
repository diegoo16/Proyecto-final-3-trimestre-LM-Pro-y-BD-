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
        if (e == null) {
            System.out.println(" Error: Empleado no puede ser nulo.");
            return;
        }
        if (e.getName() == null || e.getName().trim().isEmpty()) {
            System.out.println(" Error: El nombre del empleado es obligatorio.");
            return;
        }
        if (e.getRole() == null || e.getRole().trim().isEmpty()) {
            System.out.println(" Error: El cargo es obligatorio.");
            return;
        }
        if (e.getSalary() <= 0) {
            System.out.println(" Error: El salario debe ser mayor que 0.");
            return;
        }

        repository.create(e);
    }

    public Employee getEmployeeById(int id) {
        if (id <= 0) {
            System.out.println(" Error: ID inválido.");
            return null;
        }
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
        if (id <= 0) {
            System.out.println(" Error: ID inválido.");
            return;
        }
        try {
            repository.delete(id);
            System.out.println(" Empleado eliminado correctamente.");
        } catch (Exception e) {
            System.out.println(" Error al eliminar empleado: " + e.getMessage());
        }
    }
}