package com.Diego.service;

import com.Diego.model.OrderDetail;
import com.Diego.repository.OrderDetailRepository;
import java.util.List;

public class OrderDetailService {

    private OrderDetailRepository repository = new OrderDetailRepository();

    public void addOrderDetail(OrderDetail od) {
        repository.create(od);
    }

    public OrderDetail getOrderDetailById(int id) {
        return repository.findById(id);
    }

    public List<OrderDetail> getAllOrderDetails() {
        return repository.findAll();
    }

    public void updateOrderDetail(OrderDetail od) {
        repository.update(od);
    }

    public void deleteOrderDetail(int id) {
        repository.delete(id);
    }
}