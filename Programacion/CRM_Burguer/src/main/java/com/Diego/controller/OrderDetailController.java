package com.Diego.controller;

import com.Diego.model.OrderDetail;
import com.Diego.service.OrderDetailService;
import java.util.InputMismatchException;
import java.util.Scanner;

public class OrderDetailController {

    private final Scanner sc = new Scanner(System.in);
    private final OrderDetailService service = new OrderDetailService();

    public void menuDetalles() {
        int opcion = -1;
        do {
            System.out.println("\n=== GESTIÓN DE DETALLES DE PEDIDO ===");
            System.out.println("1. Añadir Detalle");
            System.out.println("2. Listar Detalles");
            System.out.println("3. Buscar Detalle por ID");
            System.out.println("4. Actualizar Detalle");
            System.out.println("5. Eliminar Detalle");
            System.out.println("0. Volver");
            System.out.print("Seleccione opción: ");

            try {
                opcion = sc.nextInt();
                sc.nextLine();

                switch (opcion) {
                    case 1: createOrderDetail(); break;
                    case 2: listOrderDetails(); break;
                    case 3: findOrderDetailById(); break;
                    case 4: updateOrderDetail(); break;
                    case 5: deleteOrderDetail(); break;
                    case 0: System.out.println("Volviendo..."); break;
                    default: System.out.println(" Opción no válida.");
                }
            } catch (InputMismatchException e) {
                System.out.println(" Error: Debes introducir un número.");
                sc.nextLine();
            } catch (Exception e) {
                System.out.println(" Error inesperado: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    public void createOrderDetail() {
        try {
            System.out.print("ID Pedido: ");
            int orderId = Integer.parseInt(sc.nextLine());
            System.out.print("ID Producto: ");
            int productId = Integer.parseInt(sc.nextLine());
            System.out.print("Cantidad: ");
            int quantity = Integer.parseInt(sc.nextLine());
            System.out.print("Subtotal: ");
            double subtotal = Double.parseDouble(sc.nextLine().replace(",", "."));

            OrderDetail od = new OrderDetail(0, orderId, productId, quantity, subtotal);
            service.addOrderDetail(od);
        } catch (Exception e) {
            System.out.println(" Error al crear detalle: " + e.getMessage());
        }
    }

    public void listOrderDetails() {
        try {
            var details = service.getAllOrderDetails();
            System.out.println("\n=== LISTA DE DETALLES ===");
            if (details.isEmpty()) {
                System.out.println("No hay detalles registrados.");
            } else {
                for (OrderDetail od : details) {
                    System.out.println("ID: " + od.getId() + " | Pedido: " + od.getOrderId()
                            + " | Producto: " + od.getProductId()
                            + " | Cant: " + od.getQuantity() + " | Subtotal: " + od.getSubtotal());
                }
            }
        } catch (Exception e) {
            System.out.println(" Error al listar detalles: " + e.getMessage());
        }
    }

    public void findOrderDetailById() {
        try {
            System.out.print("ID del detalle: ");
            int id = Integer.parseInt(sc.nextLine());
            OrderDetail od = service.getOrderDetailById(id);
            if (od != null) {
                System.out.println("ID: " + od.getId());
                System.out.println("Pedido ID: " + od.getOrderId());
                System.out.println("Producto ID: " + od.getProductId());
                System.out.println("Cantidad: " + od.getQuantity());
                System.out.println("Subtotal: " + od.getSubtotal());
            } else {
                System.out.println(" Detalle no encontrado.");
            }
        } catch (Exception e) {
            System.out.println(" Error: ID inválido.");
        }
    }

    public void updateOrderDetail() {
        try {
            System.out.print("ID del detalle: ");
            int id = Integer.parseInt(sc.nextLine());
            OrderDetail od = service.getOrderDetailById(id);
            if (od == null) {
                System.out.println(" Detalle no encontrado.");
                return;
            }

            System.out.print("Nuevo ID Pedido: ");
            String ord = sc.nextLine();
            if (!ord.isEmpty()) od.setOrderId(Integer.parseInt(ord));

            System.out.print("Nuevo ID Producto: ");
            String prod = sc.nextLine();
            if (!prod.isEmpty()) od.setProductId(Integer.parseInt(prod));

            System.out.print("Nueva Cantidad: ");
            String qty = sc.nextLine();
            if (!qty.isEmpty()) od.setQuantity(Integer.parseInt(qty));

            System.out.print("Nuevo Subtotal: ");
            String sub = sc.nextLine();
            if (!sub.isEmpty()) od.setSubtotal(Double.parseDouble(sub.replace(",", ".")));

            service.updateOrderDetail(od);
        } catch (Exception e) {
            System.out.println(" Error al actualizar: " + e.getMessage());
        }
    }

    public void deleteOrderDetail() {
        try {
            System.out.print("ID del detalle a eliminar: ");
            int id = Integer.parseInt(sc.nextLine());
            service.deleteOrderDetail(id);
        } catch (Exception e) {
            System.out.println(" Error al eliminar: " + e.getMessage());
        }
    }
}