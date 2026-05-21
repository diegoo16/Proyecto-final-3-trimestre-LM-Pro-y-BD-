package com.Diego.repository;

import com.Diego.model.Customer;
import com.Diego.util.DatabaseConnection;

import java.sql.*;

public class CustomerRepository implements CrudRepository<Customer>{

    @Override
    public void create(Customer c){

        String sql =
                "INSERT INTO clientes(nombre,telefono,email) VALUES(?,?,?)";

        try(Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setString(1,c.getName());
            ps.setString(2,c.getPhone());
            ps.setString(3,c.getEmail());

            ps.executeUpdate();
            System.out.println("Customer created");

        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Customer findById(int id){ return null; }
    public java.util.List<Customer> findAll(){ return null; }
    public void update(Customer c){}
    public void delete(int id){}
}