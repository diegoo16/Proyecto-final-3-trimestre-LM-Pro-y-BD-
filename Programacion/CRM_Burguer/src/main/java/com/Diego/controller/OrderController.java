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
        int customerId =
                Integer.parseInt(sc.nextLine());

        System.out.print("Employee ID: ");
        int employeeId =
                Integer.parseInt(sc.nextLine());

        System.out.print("Total: ");
        String totalText =
                sc.nextLine().replace(",", ".");
        double total =
                Double.parseDouble(totalText);

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