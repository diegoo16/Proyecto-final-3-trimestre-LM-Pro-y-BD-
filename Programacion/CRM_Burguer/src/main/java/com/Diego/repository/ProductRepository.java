package com.Diego.repository;

import com.Diego.model.Product;
import com.Diego.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository implements CrudRepository<Product> {

    @Override
    public void create(Product p) {
        String sql = "INSERT INTO productos(nombre,precio,categoria,stock) VALUES(?,?,?,?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, p.getName());
            ps.setDouble(2, p.getPrice());
            ps.setString(3, p.getCategory());
            ps.setInt(4, p.getStock());
            ps.executeUpdate();
            System.out.println("✅ Producto creado correctamente");
        } catch (Exception e) {
            System.out.println("❌ Error al crear producto: " + e.getMessage());
        }
    }

    @Override
    public Product findById(int id) {
        String sql = "SELECT * FROM productos WHERE id_producto = ?";   // ← CORREGIDO
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Product(
                        rs.getInt("id_producto"),      // ← CORREGIDO
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                        rs.getString("categoria"),
                        rs.getInt("stock")
                );
            }
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Product> findAll() {
        List<Product> lista = new ArrayList<>();
        String sql = "SELECT * FROM productos ORDER BY id_producto";   // ← CORREGIDO
        try (Connection con = DatabaseConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Product(
                        rs.getInt("id_producto"),      // ← CORREGIDO
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                        rs.getString("categoria"),
                        rs.getInt("stock")
                ));
            }
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void update(Product p) {
        String sql = "UPDATE productos SET nombre=?, precio=?, categoria=?, stock=? WHERE id_producto=?"; // ← CORREGIDO
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getName());
            ps.setDouble(2, p.getPrice());
            ps.setString(3, p.getCategory());
            ps.setInt(4, p.getStock());
            ps.setInt(5, p.getId());
            ps.executeUpdate();
            System.out.println("✅ Producto actualizado correctamente");
        } catch (Exception e) {
            System.out.println("❌ Error al actualizar: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM productos WHERE id_producto=?";   // ← CORREGIDO
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("✅ Producto eliminado correctamente");
        } catch (Exception e) {
            System.out.println("❌ Error al eliminar: " + e.getMessage());
        }
    }
}