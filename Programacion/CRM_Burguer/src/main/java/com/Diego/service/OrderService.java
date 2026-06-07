package com.Diego.service;

import com.Diego.model.Order;
import com.Diego.repository.OrderRepository;
import java.util.List;

public class OrderService {

    private final OrderRepository repository;

    public OrderService() {
        this.repository = new OrderRepository();
    }

    public void addOrder(Order o) {
        if (o == null) {
            System.out.println("Error: Pedido no puede ser nulo.");
            return;
        }
        if (o.getCustomerId() <= 0) {
            System.out.println("Error: Debe seleccionar un cliente válido.");
            return;
        }
        if (o.getEmployeeId() <= 0) {
            System.out.println("Error: Debe seleccionar un empleado válido.");
            return;
        }
        if (o.getTotal() <= 0) {
            System.out.println("Error: El total del pedido debe ser mayor que 0.");
            return;
        }

        repository.create(o);
    }

    public Order getOrderById(int id) {
        if (id <= 0) {
            System.out.println("Error: ID inválido.");
            return null;
        }
        return repository.findById(id);
    }

    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    public void updateOrder(Order o) {
        if (o == null || o.getId() <= 0) {
            System.out.println("Error: Datos inválidos para actualizar.");
            return;
        }
        repository.update(o);
    }

    public void deleteOrder(int id) {
        if (id <= 0) {
            System.out.println("Error: ID inválido.");
            return;
        }
        try {
            repository.delete(id);
            System.out.println("Pedido eliminado correctamente.");
        } catch (Exception e) {
            System.out.println("Error al eliminar pedido: " + e.getMessage());
        }
    }
}