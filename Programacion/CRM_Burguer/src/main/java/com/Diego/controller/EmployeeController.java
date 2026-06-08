package com.Diego.controller;

import com.Diego.model.Employee;
import com.Diego.service.EmployeeService;
import java.util.InputMismatchException;
import java.util.Scanner;

public class EmployeeController {

    private final Scanner sc = new Scanner(System.in);
    private final EmployeeService service = new EmployeeService();

    public void menuEmpleados() {
        int opcion = -1;
        do {
            System.out.println("\n=== GESTIÓN DE EMPLEADOS ===");
            System.out.println("1. Añadir Empleado");
            System.out.println("2. Listar Empleados");
            System.out.println("3. Buscar Empleado por ID");
            System.out.println("4. Actualizar Empleado");
            System.out.println("5. Eliminar Empleado");
            System.out.println("0. Volver");
            System.out.print("Seleccione opción: ");

            try {
                opcion = sc.nextInt();
                sc.nextLine();

                switch (opcion) {
                    case 1: createEmployee(); break;
                    case 2: listEmployees(); break;
                    case 3: findEmployeeById(); break;
                    case 4: updateEmployee(); break;
                    case 5: deleteEmployee(); break;
                    case 0: System.out.println("Volviendo..."); break;
                    default: System.out.println("Opción no válida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debes introducir un número.");
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("Error inesperado.");
                sc.nextLine();
            }
        } while (opcion != 0);
    }

    public void createEmployee() {
        System.out.print("Nombre: ");
        String name = sc.nextLine();
        System.out.print("Cargo: ");
        String role = sc.nextLine();
        System.out.print("Salario: ");
        double salary = Double.parseDouble(sc.nextLine().replace(",", "."));

        Employee e = new Employee(0, name, role, salary);
        service.addEmployee(e);
    }

    public void listEmployees() {
        service.getAllEmployees().forEach(e ->
                System.out.println(e.getId() + " | " + e.getName() + " | " + e.getRole() + " | " + e.getSalary() + "€")
        );
    }

    public void findEmployeeById() {
        System.out.print("ID del empleado: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            Employee e = service.getEmployeeById(id);
            if (e != null) {
                System.out.println("ID: " + e.getId());
                System.out.println("Nombre: " + e.getName());
                System.out.println("Cargo: " + e.getRole());
                System.out.println("Salario: " + e.getSalary());
            } else {
                System.out.println("Empleado no encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Error: ID inválido.");
        }
    }

    public void updateEmployee() {
        System.out.print("ID del empleado: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            Employee e = service.getEmployeeById(id);
            if (e == null) {
                System.out.println("Empleado no encontrado.");
                return;
            }

            System.out.print("Nuevo Nombre: ");
            String name = sc.nextLine();
            if (!name.isEmpty()) e.setName(name);

            System.out.print("Nuevo Cargo: ");
            String role = sc.nextLine();
            if (!role.isEmpty()) e.setRole(role);

            System.out.print("Nuevo Salario: ");
            String salaryStr = sc.nextLine();
            if (!salaryStr.isEmpty()) e.setSalary(Double.parseDouble(salaryStr.replace(",", ".")));

            service.updateEmployee(e);
        } catch (Exception e) {
            System.out.println("Error al actualizar empleado.");
        }
    }

    public void deleteEmployee() {
        System.out.print("ID del empleado a eliminar: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            service.deleteEmployee(id);
        } catch (Exception e) {
            System.out.println("Error al eliminar empleado.");
        }
    }
}