package com.Diego.service;

import com.Diego.model.Product;
import com.Diego.repository.ProductRepository;

public class ProductService {
    private ProductRepository repository =
            new ProductRepository();

    public void addProduct(Product p){
        repository.create(p);
    }
}