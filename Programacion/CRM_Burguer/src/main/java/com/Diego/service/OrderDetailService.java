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
        if (od == null || od.getOrderId() <= 0 || od.getProductId() <= 0 || od.getQuantity() <= 0) {
            System.out.println(" Error: Datos inválidos para el detalle del pedido.");
            return;
        }
        if (od.getSubtotal() <= 0) {
            System.out.println(" Error: El subtotal debe ser mayor que 0.");
            return;
        }
        repository.create(od);
    }

    public OrderDetail getOrderDetailById(int id) {
        return repository.findById(id);
    }

    public List<OrderDetail> getAllOrderDetails() {
        return repository.findAll();
    }

    public void updateOrderDetail(OrderDetail od) {
        if (od == null || od.getId() <= 0) {
            System.out.println(" Error: Datos inválidos para actualizar.");
            return;
        }
        repository.update(od);
    }

    public void deleteOrderDetail(int id) {
        try {
            repository.delete(id);
            System.out.println(" Detalle eliminado correctamente.");
        } catch (Exception e) {
            System.out.println(" Error al eliminar detalle: " + e.getMessage());
        }
    }
}