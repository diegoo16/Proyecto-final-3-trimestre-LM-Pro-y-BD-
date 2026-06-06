package com.Diego.service;

import com.Diego.model.Product;
import com.Diego.repository.ProductRepository;
import java.util.List;

public class ProductService {

    private final ProductRepository repository;

    public ProductService() {
        this.repository = new ProductRepository();
    }

    public void addProduct(Product p) {
        if (p == null || p.getName() == null || p.getName().trim().isEmpty()) {
            System.out.println(" Error: El nombre del producto no puede estar vacío.");
            return;
        }
        if (p.getPrice() <= 0) {
            System.out.println(" Error: El precio debe ser mayor que 0.");
            return;
        }
        if (p.getStock() < 0) {
            System.out.println(" Error: El stock no puede ser negativo.");
            return;
        }
        repository.create(p);
    }

    public Product getProductById(int id) {
        return repository.findById(id);
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public void updateProduct(Product p) {
        if (p == null || p.getId() <= 0) {
            System.out.println(" Error: Datos inválidos para actualizar.");
            return;
        }
        repository.update(p);
    }

    public void deleteProduct(int id) {
        try {
            repository.delete(id);
            System.out.println(" Producto eliminado correctamente.");
        } catch (Exception e) {
            System.out.println(" Error al eliminar producto: " + e.getMessage());
        }
    }
}