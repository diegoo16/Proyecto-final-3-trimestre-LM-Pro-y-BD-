package com.Diego.repository;

import com.Diego.model.Customer;
import com.Diego.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository implements CrudRepository<Customer> {

    @Override
    public void create(Customer c) {
        String sql = "INSERT INTO clientes(nombre,telefono,email) VALUES(?,?,?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, c.getName());
            ps.setString(2, c.getPhone());
            ps.setString(3, c.getEmail());
            ps.executeUpdate();
            System.out.println("✅ Cliente creado correctamente");
        } catch (Exception e) {
            System.out.println("❌ Error al crear cliente: " + e.getMessage());
        }
    }

    @Override
    public Customer findById(int id) {
        String sql = "SELECT * FROM clientes WHERE id_cliente = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Customer(
                        rs.getInt("id_cliente"),
                        rs.getString("nombre"),
                        rs.getString("telefono"),
                        rs.getString("email")
                );
            }
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes ORDER BY id_cliente";
        try (Connection con = DatabaseConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Customer(
                        rs.getInt("id_cliente"),
                        rs.getString("nombre"),
                        rs.getString("telefono"),
                        rs.getString("email")
                ));
            }
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void update(Customer c) {
        String sql = "UPDATE clientes SET nombre=?, telefono=?, email=? WHERE id_cliente=?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getName());
            ps.setString(2, c.getPhone());
            ps.setString(3, c.getEmail());
            ps.setInt(4, c.getId());
            ps.executeUpdate();
            System.out.println("✅ Cliente actualizado correctamente");
        } catch (Exception e) {
            System.out.println("❌ Error al actualizar: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM clientes WHERE id_cliente=?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("✅ Cliente eliminado correctamente");
        } catch (Exception e) {
            System.out.println("❌ Error al eliminar: " + e.getMessage());
        }
    }
}