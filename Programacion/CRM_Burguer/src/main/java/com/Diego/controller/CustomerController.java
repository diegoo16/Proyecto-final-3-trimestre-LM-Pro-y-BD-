package com.Diego.controller;

import com.Diego.model.Customer;
import com.Diego.service.CustomerService;

import java.util.Scanner;

public class CustomerController {

    private Scanner sc = new Scanner(System.in);
    private CustomerService service =
            new CustomerService();

    public void createCustomer(){

        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("Phone: ");
        String phone = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        Customer c =
                new Customer(0,name,phone,email);

        service.addCustomer(c);
    }
}