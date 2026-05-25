package com.Diego.repository;

import com.Diego.model.OrderDetail;
import com.Diego.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailRepository implements CrudRepository<OrderDetail> {

    @Override
    public void create(OrderDetail od) {
        String sql = "INSERT INTO detalle_pedido(id_pedido, id_producto, cantidad, subtotal) VALUES(?,?,?,?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, od.getOrderId());
            ps.setInt(2, od.getProductId());
            ps.setInt(3, od.getQuantity());
            ps.setDouble(4, od.getSubtotal());

            ps.executeUpdate();
            System.out.println(" Detalle de pedido creado correctamente");
        } catch (Exception e) {
            System.out.println(" Error al crear detalle: " + e.getMessage());
        }
    }

    @Override
    public OrderDetail findById(int id) {
        String sql = "SELECT * FROM detalle_pedido WHERE id_detalle = ?";   // ← CORREGIDO
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new OrderDetail(
                        rs.getInt("id_detalle"),           // ← CORREGIDO
                        rs.getInt("id_pedido"),
                        rs.getInt("id_producto"),
                        rs.getInt("cantidad"),
                        rs.getDouble("subtotal")
                );
            }
        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<OrderDetail> findAll() {
        List<OrderDetail> lista = new ArrayList<>();
        String sql = "SELECT * FROM detalle_pedido ORDER BY id_detalle";   // ← CORREGIDO
        try (Connection con = DatabaseConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new OrderDetail(
                        rs.getInt("id_detalle"),          // ← CORREGIDO
                        rs.getInt("id_pedido"),
                        rs.getInt("id_producto"),
                        rs.getInt("cantidad"),
                        rs.getDouble("subtotal")
                ));
            }
        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void update(OrderDetail od) {
        String sql = "UPDATE detalle_pedido SET id_pedido=?, id_producto=?, cantidad=?, subtotal=? WHERE id_detalle=?"; // ← CORREGIDO
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, od.getOrderId());
            ps.setInt(2, od.getProductId());
            ps.setInt(3, od.getQuantity());
            ps.setDouble(4, od.getSubtotal());
            ps.setInt(5, od.getId());

            ps.executeUpdate();
            System.out.println(" Detalle actualizado correctamente");
        } catch (Exception e) {
            System.out.println(" Error al actualizar detalle: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM detalle_pedido WHERE id_detalle=?";   // ← CORREGIDO
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println(" Detalle eliminado correctamente");
        } catch (Exception e) {
            System.out.println(" Error al eliminar detalle: " + e.getMessage());
        }
    }
}