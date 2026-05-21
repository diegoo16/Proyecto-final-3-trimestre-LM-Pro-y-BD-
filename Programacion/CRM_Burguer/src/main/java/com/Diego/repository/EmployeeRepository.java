package com.Diego.repository;

import com.Diego.model.Employee;
import com.Diego.util.DatabaseConnection;

import java.sql.*;
import java.util.List;

public class EmployeeRepository implements CrudRepository<Employee>{

    @Override
    public void create(Employee e){

        String sql =
                "INSERT INTO empleados(nombre,cargo,salario) VALUES(?,?,?)";

        try(Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setString(1,e.getName());
            ps.setString(2,e.getRole());
            ps.setDouble(3,e.getSalary());

            ps.executeUpdate();
            System.out.println("Employee created");

        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public Employee findById(int id){ return null; }

    public List<Employee> findAll(){ return null; }

    public void update(Employee e){}

    public void delete(int id){}
}