package com.Diego.util;

import com.Diego.model.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvExporter {

    public static void exportCustomers(List<Customer> customers) {
        exportToCSV("clientes.csv", "ID,Nombre,Teléfono,Email", customers);
    }

    public static void exportEmployees(List<Employee> employees) {
        exportToCSV("empleados.csv", "ID,Nombre,Cargo,Salario", employees);
    }

    public static void exportProducts(List<Product> products) {
        exportToCSV("productos.csv", "ID,Nombre,Precio,Categoría,Stock", products);
    }

    public static void exportOrders(List<Order> orders) {
        exportToCSV("pedidos.csv", "ID,Fecha,ID_Cliente,ID_Empleado,Total", orders);
    }

    public static void exportOrderDetails(List<OrderDetail> details) {
        exportToCSV("detalles_pedido.csv", "ID,ID_Pedido,ID_Producto,Cantidad,Subtotal", details);
    }

    // Método genérico privado
    private static <T> void exportToCSV(String filename, String header, List<T> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("⚠ No hay datos para exportar a " + filename);
            return;
        }

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(header + "\n");

            for (T item : list) {
                String line = formatAsCSV(item);
                writer.write(line + "\n");
            }

            System.out.println(" Exportación completada: " + filename + " (" + list.size() + " registros)");

        } catch (IOException e) {
            System.out.println(" Error al exportar a CSV: " + e.getMessage());
        }
    }

    // Método que formatea según el tipo de objeto
    private static String formatAsCSV(Object item) {
        if (item instanceof Customer c) {
            return c.getId() + "," + escapeCSV(c.getName()) + "," +
                    escapeCSV(c.getPhone()) + "," + escapeCSV(c.getEmail());
        }
        else if (item instanceof Employee e) {
            return e.getId() + "," + escapeCSV(e.getName()) + "," +
                    escapeCSV(e.getRole()) + "," + e.getSalary();
        }
        else if (item instanceof Product p) {
            return p.getId() + "," + escapeCSV(p.getName()) + "," +
                    p.getPrice() + "," + escapeCSV(p.getCategory()) + "," + p.getStock();
        }
        else if (item instanceof Order o) {
            return o.getId() + "," + o.getDate() + "," +
                    o.getCustomerId() + "," + o.getEmployeeId() + "," + o.getTotal();
        }
        else if (item instanceof OrderDetail od) {
            return od.getId() + "," + od.getOrderId() + "," +
                    od.getProductId() + "," + od.getQuantity() + "," + od.getSubtotal();
        }
        return "";
    }

    private static String escapeCSV(String field) {
        if (field == null) return "";
        String escaped = field.replace("\"", "\"\"");
        if (field.contains(",") || field.contains("\"") || field.contains("\n")) {
            return "\"" + escaped + "\"";
        }
        return escaped;
    }
}