package com.Diego.controller;

import com.Diego.model.Employee;
import com.Diego.service.EmployeeService;
import java.util.Scanner;

public class EmployeeController {

    private Scanner sc = new Scanner(System.in);
    private EmployeeService service =
            new EmployeeService();

    public void createEmployee(){

        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("Role: ");
        String role = sc.nextLine();

        System.out.print("Salary: ");
        double salary = sc.nextDouble();
        sc.nextLine();

        Employee e =
                new Employee(0,name,role,salary);

        service.addEmployee(e);
    }
}