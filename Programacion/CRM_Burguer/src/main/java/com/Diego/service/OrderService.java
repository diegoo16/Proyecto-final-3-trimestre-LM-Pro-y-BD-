package com.Diego.service;

import com.Diego.model.Order;
import com.Diego.repository.OrderRepository;

public class OrderService {
    private OrderRepository repository =
            new OrderRepository();

    public void addOrder(Order o){
        repository.create(o);
    }
}