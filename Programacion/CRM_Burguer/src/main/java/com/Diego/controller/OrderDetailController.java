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
        int orderId =
                Integer.parseInt(sc.nextLine());

        System.out.print("Product ID: ");
        int productId =
                Integer.parseInt(sc.nextLine());

        System.out.print("Quantity: ");
        int quantity =
                Integer.parseInt(sc.nextLine());

        System.out.print("Subtotal: ");
        String subtotalText =
                sc.nextLine().replace(",", ".");
        double subtotal =
                Double.parseDouble(subtotalText);

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