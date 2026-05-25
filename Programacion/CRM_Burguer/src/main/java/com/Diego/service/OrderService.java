package com.Diego.service;

import com.Diego.model.Order;
import com.Diego.repository.OrderRepository;
import java.util.List;

public class OrderService {

    private OrderRepository repository = new OrderRepository();

    public void addOrder(Order o) {
        repository.create(o);
    }

    public Order getOrderById(int id) {
        return repository.findById(id);
    }

    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    public void updateOrder(Order o) {
        repository.update(o);
    }

    public void deleteOrder(int id) {
        repository.delete(id);
    }
}