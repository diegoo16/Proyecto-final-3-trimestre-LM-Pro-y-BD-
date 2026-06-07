package com.Diego.model;

public class Employee extends BaseEntity {

    private String name;
    private String role;
    private double salary;

    public Employee() {
        super();
    }

    public Employee(int id, String name, String role, double salary) {
        super(id);
        this.name = name;
        this.role = role;
        this.salary = salary;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    @Override
    public String toString() {
        return super.toString() + " | " + name + " | Cargo: " + role + " | Salario: " + salary + "€";
    }
}