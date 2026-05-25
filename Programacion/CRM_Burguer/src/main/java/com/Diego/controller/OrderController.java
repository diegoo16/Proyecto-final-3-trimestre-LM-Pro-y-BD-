package com.Diego.controller;

import com.Diego.model.Order;
import com.Diego.service.OrderService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class OrderController {

    private Scanner sc = new Scanner(System.in);
    private OrderService service = new OrderService();

    public void menuPedidos() {
        int opcion;
        do {
            System.out.println("\n=== GESTIÓN DE PEDIDOS ===");
            System.out.println("1. Crear Nuevo Pedido");
            System.out.println("2. Listar Pedidos");
            System.out.println("3. Buscar Pedido por ID");
            System.out.println("4. Actualizar Pedido");
            System.out.println("5. Eliminar Pedido");
            System.out.println("0. Volver");
            System.out.print("Seleccione opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1: createOrder(); break;
                case 2: listOrders(); break;
                case 3: findOrderById(); break;
                case 4: updateOrder(); break;
                case 5: deleteOrder(); break;
                case 0: break;
                default: System.out.println("❌ Opción no válida");
            }
        } while (opcion != 0);
    }

    public void createOrder() {
        System.out.print("ID Cliente: ");
        int customerId = Integer.parseInt(sc.nextLine());
        System.out.print("ID Empleado: ");
        int employeeId = Integer.parseInt(sc.nextLine());
        System.out.print("Total: ");
        double total = Double.parseDouble(sc.nextLine().replace(",", "."));

        Order o = new Order(0, LocalDateTime.now(), customerId, employeeId, total);
        service.addOrder(o);
    }

    public void listOrders() {
        List<Order> orders = service.getAllOrders();
        System.out.println("\n=== LISTA DE PEDIDOS ===");
        for (Order o : orders) {
            System.out.println(o.getId() + " | Fecha: " + o.getDate() + " | Cliente: " + o.getCustomerId()
                    + " | Empleado: " + o.getEmployeeId() + " | Total: " + o.getTotal() + "€");
        }
    }

    public void findOrderById() {
        System.out.print("ID del pedido: ");
        int id = Integer.parseInt(sc.nextLine());
        Order o = service.getOrderById(id);
        if (o != null) {
            System.out.println("ID: " + o.getId());
            System.out.println("Fecha: " + o.getDate());
            System.out.println("ID Cliente: " + o.getCustomerId());
            System.out.println("ID Empleado: " + o.getEmployeeId());
            System.out.println("Total: " + o.getTotal());
        } else {
            System.out.println("❌ Pedido no encontrado");
        }
    }

    public void updateOrder() {
        System.out.print("ID del pedido: ");
        int id = Integer.parseInt(sc.nextLine());
        Order o = service.getOrderById(id);
        if (o == null) return;

        System.out.print("Nuevo ID Cliente: ");
        String cust = sc.nextLine();
        if (!cust.isEmpty()) o.setCustomerId(Integer.parseInt(cust));

        System.out.print("Nuevo ID Empleado: ");
        String emp = sc.nextLine();
        if (!emp.isEmpty()) o.setEmployeeId(Integer.parseInt(emp));

        System.out.print("Nuevo Total: ");
        String totalStr = sc.nextLine();
        if (!totalStr.isEmpty()) o.setTotal(Double.parseDouble(totalStr.replace(",", ".")));

        service.updateOrder(o);
    }

    public void deleteOrder() {
        System.out.print("ID del pedido a eliminar: ");
        int id = Integer.parseInt(sc.nextLine());
        service.deleteOrder(id);
    }
}