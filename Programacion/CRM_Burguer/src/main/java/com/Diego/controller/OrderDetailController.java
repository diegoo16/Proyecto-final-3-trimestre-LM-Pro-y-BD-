package com.Diego.controller;

import com.Diego.model.OrderDetail;
import com.Diego.service.OrderDetailService;

import java.util.Scanner;

public class OrderDetailController {

    private Scanner sc = new Scanner(System.in);
    private OrderDetailService service =
            new OrderDetailService();

    public void createOrderDetail(){

        System.out.print("Order ID: ");
        int orderId = sc.nextInt();

        System.out.print("Product ID: ");
        int productId = sc.nextInt();

        System.out.print("Quantity: ");
        int quantity = sc.nextInt();

        System.out.print("Subtotal: ");
        double subtotal = sc.nextDouble();
        sc.nextLine();

        OrderDetail od =
                new OrderDetail(
                        0,
                        orderId,
                        productId,
                        quantity,
                        subtotal
                );

        service.addOrderDetail(od);
    }
}