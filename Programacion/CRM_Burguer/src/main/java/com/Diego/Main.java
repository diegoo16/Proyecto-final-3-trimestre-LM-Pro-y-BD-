package com.Diego;

import com.Diego.controller.*;
import com.Diego.service.CustomerService;
import com.Diego.model.Customer;
import com.Diego.util.CsvExporter;      // ← AÑADIDO ESTE IMPORT
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        CustomerController customerCtrl = new CustomerController();
        EmployeeController employeeCtrl = new EmployeeController();
        ProductController productCtrl = new ProductController();
        OrderController orderCtrl = new OrderController();
        OrderDetailController detailCtrl = new OrderDetailController();

        int option;

        do {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("           CRM BURGUER - MENÚ PRINCIPAL");
            System.out.println("=".repeat(50));
            System.out.println("1. Gestión de Clientes");
            System.out.println("2. Gestión de Empleados");
            System.out.println("3. Gestión de Productos");
            System.out.println("4. Gestión de Pedidos");
            System.out.println("5. Gestión de Detalles de Pedido");
            System.out.println("6. Exportar Clientes a CSV");
            System.out.println("0. Salir");
            System.out.println("=".repeat(50));
            System.out.print("Seleccione una opción: ");

            option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1: customerCtrl.menuClientes(); break;
                case 2: employeeCtrl.menuEmpleados(); break;
                case 3: productCtrl.menuProductos(); break;
                case 4: orderCtrl.menuPedidos(); break;
                case 5: detailCtrl.menuDetalles(); break;
                case 6: exportCustomersToCSV(); break;
                case 0: System.out.println(" Saliendo del sistema CRM_Burguer..."); break;
                default: System.out.println(" Opción no válida.");
            }
        } while (option != 0);

        sc.close();
    }

    private static void exportCustomersToCSV() {
        CustomerService customerService = new CustomerService();
        List<Customer> customers = customerService.getAllCustomers();

        if (customers.isEmpty()) {
            System.out.println(" No hay clientes para exportar.");
        } else {
            CsvExporter.exportCustomers(customers);
        }
    }
}