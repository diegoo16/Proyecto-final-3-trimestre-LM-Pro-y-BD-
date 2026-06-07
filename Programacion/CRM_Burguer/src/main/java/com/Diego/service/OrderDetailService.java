package com.Diego.service;

import com.Diego.model.OrderDetail;
import com.Diego.repository.OrderDetailRepository;
import java.util.List;

public class OrderDetailService {

    private final OrderDetailRepository repository;

    public OrderDetailService() {
        this.repository = new OrderDetailRepository();
    }

    public void addOrderDetail(OrderDetail od) {
        if (od == null) {
            System.out.println("Error: Detalle no puede ser nulo.");
            return;
        }
        if (od.getOrderId() <= 0) {
            System.out.println("Error: Debe indicar un pedido válido.");
            return;
        }
        if (od.getProductId() <= 0) {
            System.out.println("Error: Debe indicar un producto válido.");
            return;
        }
        if (od.getQuantity() <= 0) {
            System.out.println("Error: La cantidad debe ser mayor que 0.");
            return;
        }
        if (od.getSubtotal() <= 0) {
            System.out.println("Error: El subtotal debe ser mayor que 0.");
            return;
        }

        repository.create(od);
    }

    public OrderDetail getOrderDetailById(int id) {
        if (id <= 0) {
            System.out.println("Error: ID inválido.");
            return null;
        }
        return repository.findById(id);
    }

    public List<OrderDetail> getAllOrderDetails() {
        return repository.findAll();
    }

    public void updateOrderDetail(OrderDetail od) {
        if (od == null || od.getId() <= 0) {
            System.out.println("Error: Datos inválidos para actualizar.");
            return;
        }
        repository.update(od);
    }

    public void deleteOrderDetail(int id) {
        if (id <= 0) {
            System.out.println("Error: ID inválido.");
            return;
        }
        try {
            repository.delete(id);
            System.out.println("Detalle eliminado correctamente.");
        } catch (Exception e) {
            System.out.println("Error al eliminar detalle: " + e.getMessage());
        }
    }
}