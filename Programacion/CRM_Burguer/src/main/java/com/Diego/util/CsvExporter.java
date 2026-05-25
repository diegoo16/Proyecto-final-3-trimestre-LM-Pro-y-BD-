package com.Diego.util;

import com.Diego.model.Customer;
import com.Diego.model.Employee;
import com.Diego.model.Product;
import com.Diego.model.Order;
import com.Diego.model.OrderDetail;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvExporter {

    // Exportar Clientes
    public static void exportCustomers(List<Customer> customers) {
        exportToCSV("clientes.csv", "ID,Nombre,Teléfono,Email", customers);
    }

    // Exportar Empleados
    public static void exportEmployees(List<Employee> employees) {
        exportToCSV("empleados.csv", "ID,Nombre,Cargo,Salario", employees);
    }

    // Exportar Productos
    public static void exportProducts(List<Product> products) {
        exportToCSV("productos.csv", "ID,Nombre,Precio,Categoría,Stock", products);
    }

    // Exportar Pedidos
    public static void exportOrders(List<Order> orders) {
        exportToCSV("pedidos.csv", "ID,Fecha,ID_Cliente,ID_Empleado,Total", orders);
    }

    // Método genérico privado
    private static <T> void exportToCSV(String filename, String header, List<T> list) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(header + "\n");

            for (T item : list) {
                String line = "";

                if (item instanceof Customer c) {
                    line = c.getId() + "," + escapeCSV(c.getName()) + "," +
                            escapeCSV(c.getPhone()) + "," + escapeCSV(c.getEmail());
                } else if (item instanceof Employee e) {
                    line = e.getId() + "," + escapeCSV(e.getName()) + "," +
                            escapeCSV(e.getRole()) + "," + e.getSalary();
                } else if (item instanceof Product p) {
                    line = p.getId() + "," + escapeCSV(p.getName()) + "," +
                            p.getPrice() + "," + escapeCSV(p.getCategory()) + "," + p.getStock();
                } else if (item instanceof Order o) {
                    line = o.getId() + "," + o.getDate() + "," +
                            o.getCustomerId() + "," + o.getEmployeeId() + "," + o.getTotal();
                } else if (item instanceof OrderDetail od) {
                    line = od.getId() + "," + od.getOrderId() + "," +
                            od.getProductId() + "," + od.getQuantity() + "," + od.getSubtotal();
                }

                writer.write(line + "\n");
            }

            System.out.println(" Exportación completada: " + filename);

        } catch (IOException e) {
            System.out.println(" Error al exportar a CSV: " + e.getMessage());
        }
    }

    // Evita problemas con comas en los campos
    private static String escapeCSV(String field) {
        if (field == null) return "";
        if (field.contains(",") || field.contains("\"") || field.contains("\n")) {
            return "\"" + field.replace("\"", "\"\"") + "\"";
        }
        return field;
    }
}