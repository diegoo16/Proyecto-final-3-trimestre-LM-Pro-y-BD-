package com.Diego.service;

import com.Diego.model.OrderDetail;
import com.Diego.repository.OrderDetailRepository;

public class OrderDetailService {
    private OrderDetailRepository repository =
            new OrderDetailRepository();

    public void addOrderDetail(OrderDetail od){
        repository.create(od);
    }
}