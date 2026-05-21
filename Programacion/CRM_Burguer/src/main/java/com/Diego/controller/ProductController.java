package com.Diego.controller;

import com.Diego.model.Product;
import com.Diego.service.ProductService;
import java.util.Scanner;

public class ProductController {

    private Scanner sc = new Scanner(System.in);
    private ProductService service =
            new ProductService();

    public void createProduct(){

        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("Price: ");
        double price = sc.nextDouble();
        sc.nextLine();

        System.out.print("Category: ");
        String category = sc.nextLine();

        System.out.print("Stock: ");
        int stock = sc.nextInt();
        sc.nextLine();

        Product p =
                new Product(0,name,price,category,stock);

        service.addProduct(p);
    }
}