package com.Diego.controller;

import com.Diego.model.Customer;
import com.Diego.service.CustomerService;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CustomerController {

    private final Scanner sc = new Scanner(System.in);
    private final CustomerService service = new CustomerService();

    public void menuClientes() {
        int opcion = -1;
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
                    default: System.out.println("Opción no válida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debes introducir un número.");
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("Error inesperado.");
                sc.nextLine();
            }
        } while (opcion != 0);
    }

    public void createCustomer() {
        System.out.print("Nombre: ");
        String name = sc.nextLine();
        System.out.print("Teléfono: ");
        String phone = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();

        Customer c = new Customer(0, name, phone, email);
        service.addCustomer(c);
    }

    public void listCustomers() {
        service.getAllCustomers().forEach(c ->
                System.out.println(c.getId() + " | " + c.getName() + " | " + c.getPhone() + " | " + c.getEmail())
        );
    }

    public void findCustomerById() {
        System.out.print("ID del cliente: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            Customer c = service.getCustomerById(id);
            if (c != null) {
                System.out.println("ID: " + c.getId());
                System.out.println("Nombre: " + c.getName());
                System.out.println("Teléfono: " + c.getPhone());
                System.out.println("Email: " + c.getEmail());
            } else {
                System.out.println("Cliente no encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Error: ID inválido.");
        }
    }

    public void updateCustomer() {
        System.out.print("ID del cliente a actualizar: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            Customer c = service.getCustomerById(id);
            if (c == null) {
                System.out.println("Cliente no encontrado.");
                return;
            }

            System.out.print("Nuevo Nombre: ");
            String name = sc.nextLine();
            if (!name.isEmpty()) c.setName(name);

            System.out.print("Nuevo Teléfono: ");
            String phone = sc.nextLine();
            if (!phone.isEmpty()) c.setPhone(phone);

            System.out.print("Nuevo Email: ");
            String email = sc.nextLine();
            if (!email.isEmpty()) c.setEmail(email);

            service.updateCustomer(c);
        } catch (Exception e) {
            System.out.println("Error al actualizar cliente.");
        }
    }

    public void deleteCustomer() {
        System.out.print("ID del cliente a eliminar: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            service.deleteCustomer(id);
        } catch (Exception e) {
            System.out.println("Error al eliminar cliente.");
        }
    }
}