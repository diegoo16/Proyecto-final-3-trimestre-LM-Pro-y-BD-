package com.Diego.repository;

import com.Diego.model.Order;
import com.Diego.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository implements CrudRepository<Order> {

    @Override
    public void create(Order o) {
        Connection con = null;
        try {
            con = DatabaseConnection.getConnection();
            con.setAutoCommit(false);

            String sql = "INSERT INTO pedidos(fecha, id_cliente, id_empleado, total) VALUES(?,?,?,?)";

            try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setTimestamp(1, Timestamp.valueOf(o.getDate()));
                ps.setInt(2, o.getCustomerId());
                ps.setInt(3, o.getEmployeeId());
                ps.setDouble(4, o.getTotal());

                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    o.setId(rs.getInt(1));
                }
            }

            con.commit();
            System.out.println("✅ Pedido creado correctamente (ID: " + o.getId() + ")");

        } catch (Exception e) {
            if (con != null) try { con.rollback(); } catch (SQLException ex) {}
            System.out.println("❌ Error al crear pedido: " + e.getMessage());
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException e) {}
            }
        }
    }

    @Override
    public Order findById(int id) {
        String sql = "SELECT * FROM pedidos WHERE id_pedido = ?";   // ← CORREGIDO
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Order(
                        rs.getInt("id_pedido"),        // ← CORREGIDO
                        rs.getTimestamp("fecha").toLocalDateTime(),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_empleado"),
                        rs.getDouble("total")
                );
            }
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Order> findAll() {
        List<Order> lista = new ArrayList<>();
        String sql = "SELECT * FROM pedidos ORDER BY id_pedido DESC";   // ← CORREGIDO
        try (Connection con = DatabaseConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Order(
                        rs.getInt("id_pedido"),        // ← CORREGIDO
                        rs.getTimestamp("fecha").toLocalDateTime(),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_empleado"),
                        rs.getDouble("total")
                ));
            }
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void update(Order o) {
        String sql = "UPDATE pedidos SET fecha=?, id_cliente=?, id_empleado=?, total=? WHERE id_pedido=?"; // ← CORREGIDO
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(o.getDate()));
            ps.setInt(2, o.getCustomerId());
            ps.setInt(3, o.getEmployeeId());
            ps.setDouble(4, o.getTotal());
            ps.setInt(5, o.getId());

            ps.executeUpdate();
            System.out.println("✅ Pedido actualizado correctamente");
        } catch (Exception e) {
            System.out.println("❌ Error al actualizar pedido: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM pedidos WHERE id_pedido=?";   // ← CORREGIDO
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("✅ Pedido eliminado correctamente");
        } catch (Exception e) {
            System.out.println("❌ Error al eliminar pedido: " + e.getMessage());
        }
    }
}