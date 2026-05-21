package com.Diego.repository;

import com.Diego.model.Order;
import com.Diego.util.DatabaseConnection;

import java.sql.*;
import java.util.List;

public class OrderRepository implements CrudRepository<Order>{

    @Override
    public void create(Order o){

        String sql =
                "INSERT INTO pedidos(fecha,id_cliente,id_empleado,total) VALUES(?,?,?,?)";

        try(Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setTimestamp(1, Timestamp.valueOf(o.getDate()));
            ps.setInt(2,o.getCustomerId());
            ps.setInt(3,o.getEmployeeId());
            ps.setDouble(4,o.getTotal());

            ps.executeUpdate();
            System.out.println("Order created");

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Order findById(int id){ return null; }
    public List<Order> findAll(){ return null; }
    public void update(Order o){}
    public void delete(int id){}
}