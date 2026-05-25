package com.Diego.repository;

import com.Diego.model.Employee;
import com.Diego.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository implements CrudRepository<Employee> {

    @Override
    public void create(Employee e) {
        String sql = "INSERT INTO empleados(nombre,cargo,salario) VALUES(?,?,?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, e.getName());
            ps.setString(2, e.getRole());
            ps.setDouble(3, e.getSalary());
            ps.executeUpdate();
            System.out.println("✅ Empleado creado correctamente");
        } catch (Exception ex) {
            System.out.println("❌ Error al crear empleado: " + ex.getMessage());
        }
    }

    @Override
    public Employee findById(int id) {
        String sql = "SELECT * FROM empleados WHERE id_empleado = ?";   // ← CORREGIDO
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Employee(
                        rs.getInt("id_empleado"),      // ← CORREGIDO
                        rs.getString("nombre"),
                        rs.getString("cargo"),
                        rs.getDouble("salario")
                );
            }
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> lista = new ArrayList<>();
        String sql = "SELECT * FROM empleados ORDER BY id_empleado";   // ← CORREGIDO
        try (Connection con = DatabaseConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Employee(
                        rs.getInt("id_empleado"),      // ← CORREGIDO
                        rs.getString("nombre"),
                        rs.getString("cargo"),
                        rs.getDouble("salario")
                ));
            }
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void update(Employee e) {
        String sql = "UPDATE empleados SET nombre=?, cargo=?, salario=? WHERE id_empleado=?";  // ← CORREGIDO
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, e.getName());
            ps.setString(2, e.getRole());
            ps.setDouble(3, e.getSalary());
            ps.setInt(4, e.getId());
            ps.executeUpdate();
            System.out.println("✅ Empleado actualizado correctamente");
        } catch (Exception ex) {
            System.out.println("❌ Error al actualizar: " + ex.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM empleados WHERE id_empleado=?";   // ← CORREGIDO
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("✅ Empleado eliminado correctamente");
        } catch (Exception e) {
            System.out.println("❌ Error al eliminar: " + e.getMessage());
        }
    }
}