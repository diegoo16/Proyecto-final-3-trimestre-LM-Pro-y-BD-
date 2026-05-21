package com.Diego.repository;

import com.Diego.model.Product;
import com.Diego.util.DatabaseConnection;

import java.sql.*;
import java.util.List;

public class ProductRepository implements CrudRepository<Product>{

    @Override
    public void create(Product p){

        String sql =
                "INSERT INTO productos(nombre,precio,categoria,stock) VALUES(?,?,?,?)";

        try(Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setString(1,p.getName());
            ps.setDouble(2,p.getPrice());
            ps.setString(3,p.getCategory());
            ps.setInt(4,p.getStock());

            ps.executeUpdate();
            System.out.println("Product created");

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Product findById(int id){ return null; }
    public List<Product> findAll(){ return null; }
    public void update(Product p){}
    public void delete(int id){}
}