package com.Diego.service;

import com.Diego.model.Product;
import com.Diego.repository.ProductRepository;
import java.util.List;

public class ProductService {

    private ProductRepository repository = new ProductRepository();

    public void addProduct(Product p) {
        repository.create(p);
    }

    public Product getProductById(int id) {
        return repository.findById(id);
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public void updateProduct(Product p) {
        repository.update(p);
    }

    public void deleteProduct(int id) {
        repository.delete(id);
    }
}