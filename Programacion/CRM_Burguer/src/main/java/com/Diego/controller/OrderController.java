package com.Diego.controller;

import com.Diego.model.Order;
import com.Diego.service.OrderService;

import java.time.LocalDateTime;
import java.util.Scanner;

public class OrderController {

    private Scanner sc = new Scanner(System.in);
    private OrderService service =
            new OrderService();

    public void createOrder(){

        System.out.print("Customer ID: ");
        int customerId = sc.nextInt();

        System.out.print("Employee ID: ");
        int employeeId = sc.nextInt();

        System.out.print("Total: ");
        double total = sc.nextDouble();
        sc.nextLine();

        Order o =
                new Order(
                        0,
                        LocalDateTime.now(),
                        customerId,
                        employeeId,
                        total
                );

        service.addOrder(o);
    }
}