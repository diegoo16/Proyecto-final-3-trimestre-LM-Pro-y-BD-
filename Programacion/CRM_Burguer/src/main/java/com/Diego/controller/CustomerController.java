package com.Diego.controller;

import com.Diego.model.Customer;
import com.Diego.model.Order;
import com.Diego.service.CustomerService;
import com.Diego.service.OrderService;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CustomerController {

    private Scanner sc = new Scanner(System.in);
    private CustomerService service = new CustomerService();

    public void menuClientes() {
        int opcion;
        do {
            System.out.println("\n=== GESTIÓN DE CLIENTES ===");
            System.out.println("1. Añadir Cliente");
            System.out.println("2. Listar Clientes");
            System.out.println("3. Buscar Cliente por ID");
            System.out.println("4. Actualizar Cliente");
            System.out.println("5. Eliminar Cliente");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione opción: ");

            try {
                opcion = sc.nextInt();
                sc.nextLine();

                switch (opcion) {
                    case 1: createCustomer(); break;
                    case 2: listCustomers(); break;
                    case 3: findCustomerById(); break;
                    case 4: updateCustomer(); break;
                    case 5: deleteCustomer(); break;
                    case 0: System.out.println("Volviendo al menú principal..."); break;
                    default: System.out.println(" Opción no válida");
                }
            } catch (InputMismatchException e) {
                System.out.println(" Error: Debes introducir un número.");
                sc.nextLine();
                opcion = -1;
            }
        } while (opcion != 0);
    }

    public void createCustomer() {
        try {
            System.out.print("Nombre: ");
            String name = sc.nextLine();
            System.out.print("Teléfono: ");
            String phone = sc.nextLine();
            System.out.print("Email: ");
            String email = sc.nextLine();

            Customer c = new Customer(0, name, phone, email);
            service.addCustomer(c);
        } catch (Exception e) {
            System.out.println(" Error al crear cliente: " + e.getMessage());
        }
    }

    public void listCustomers() {
        try {
            List<Customer> customers = service.getAllCustomers();
            System.out.println("\n=== LISTA DE CLIENTES ===");
            if (customers.isEmpty()) {
                System.out.println("No hay clientes registrados.");
            } else {
                for (Customer c : customers) {
                    System.out.println(c.getId() + " | " + c.getName() + " | " + c.getPhone() + " | " + c.getEmail());
                }
            }
        } catch (Exception e) {
            System.out.println(" Error al listar clientes: " + e.getMessage());
        }
    }

    public void findCustomerById() {
        try {
            System.out.print("ID del cliente: ");
            int id = Integer.parseInt(sc.nextLine());
            Customer c = service.getCustomerById(id);
            if (c != null) {
                System.out.println("ID: " + c.getId());
                System.out.println("Nombre: " + c.getName());
                System.out.println("Teléfono: " + c.getPhone());
                System.out.println("Email: " + c.getEmail());
            } else {
                System.out.println(" Cliente no encontrado");
            }
        } catch (Exception e) {
            System.out.println(" Error: ID inválido");
        }
    }

    public void updateCustomer() {
        try {
            System.out.print("ID del cliente a actualizar: ");
            int id = Integer.parseInt(sc.nextLine());
            Customer c = service.getCustomerById(id);
            if (c == null) {
                System.out.println(" Cliente no encontrado");
                return;
            }

            System.out.print("Nuevo Nombre (" + c.getName() + "): ");
            String name = sc.nextLine();
            if (!name.isEmpty()) c.setName(name);

            System.out.print("Nuevo Teléfono (" + c.getPhone() + "): ");
            String phone = sc.nextLine();
            if (!phone.isEmpty()) c.setPhone(phone);

            System.out.print("Nuevo Email (" + c.getEmail() + "): ");
            String email = sc.nextLine();
            if (!email.isEmpty()) c.setEmail(email);

            service.updateCustomer(c);
        } catch (Exception e) {
            System.out.println(" Error al actualizar: " + e.getMessage());
        }
    }

    public void deleteCustomer() {
        try {
            System.out.print("ID del cliente a eliminar: ");
            int id = Integer.parseInt(sc.nextLine());

            OrderService orderService = new OrderService();
            List<Order> pedidos = orderService.getAllOrders();

            boolean tienePedidos = false;
            for (Order o : pedidos) {
                if (o.getCustomerId() == id) {
                    tienePedidos = true;
                    break;
                }
            }

            if (tienePedidos) {
                System.out.println(" No se puede eliminar el cliente.");
                System.out.println("   Tiene pedidos asociados. Elimine primero los pedidos.");
            } else {
                service.deleteCustomer(id);
            }
        } catch (Exception e) {
            System.out.println(" Error al eliminar: " + e.getMessage());
        }
    }
}