package com.Diego.repository;

import com.Diego.model.OrderDetail;
import com.Diego.util.DatabaseConnection;

import java.sql.*;
import java.util.List;

public class OrderDetailRepository implements CrudRepository<OrderDetail>{

    @Override
    public void create(OrderDetail od){

        String sql =
                "INSERT INTO detalle_pedido(id_pedido,id_producto,cantidad,subtotal) VALUES(?,?,?,?)";

        try(Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setInt(1,od.getOrderId());
            ps.setInt(2,od.getProductId());
            ps.setInt(3,od.getQuantity());
            ps.setDouble(4,od.getSubtotal());

            ps.executeUpdate();
            System.out.println("Order detail created");

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public OrderDetail findById(int id){ return null; }
    public List<OrderDetail> findAll(){ return null; }
    public void update(OrderDetail od){}
    public void delete(int id){}
}