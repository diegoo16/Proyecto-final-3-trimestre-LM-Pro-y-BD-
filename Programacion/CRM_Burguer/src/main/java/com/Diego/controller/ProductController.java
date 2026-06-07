package com.Diego.controller;

import com.Diego.model.Product;
import com.Diego.service.ProductService;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ProductController {

    private final Scanner sc = new Scanner(System.in);
    private final ProductService service = new ProductService();

    public void menuProductos() {
        int opcion = -1;
        do {
            System.out.println("\n=== GESTIÓN DE PRODUCTOS ===");
            System.out.println("1. Añadir Producto");
            System.out.println("2. Listar Productos");
            System.out.println("3. Buscar Producto por ID");
            System.out.println("4. Actualizar Producto");
            System.out.println("5. Eliminar Producto");
            System.out.println("0. Volver");
            System.out.print("Seleccione opción: ");

            try {
                opcion = sc.nextInt();
                sc.nextLine();

                switch (opcion) {
                    case 1: createProduct(); break;
                    case 2: listProducts(); break;
                    case 3: findProductById(); break;
                    case 4: updateProduct(); break;
                    case 5: deleteProduct(); break;
                    case 0: System.out.println("Volviendo..."); break;
                    default: System.out.println("❌ Opción no válida.");
                }
            } catch (InputMismatchException e) {
                System.out.println(" Error: Debes introducir un número.");
                sc.nextLine();
            } catch (Exception e) {
                System.out.println(" Error inesperado: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    public void createProduct() {
        try {
            System.out.print("Nombre: ");
            String name = sc.nextLine();
            System.out.print("Precio: ");
            double price = Double.parseDouble(sc.nextLine().replace(",", "."));
            System.out.print("Categoría: ");
            String category = sc.nextLine();
            System.out.print("Stock: ");
            int stock = Integer.parseInt(sc.nextLine());

            Product p = new Product(0, name, price, category, stock);
            service.addProduct(p);
        } catch (Exception e) {
            System.out.println(" Error al crear producto: " + e.getMessage());
        }
    }

    public void listProducts() {
        try {
            var products = service.getAllProducts();
            System.out.println("\n=== LISTA DE PRODUCTOS ===");
            if (products.isEmpty()) {
                System.out.println("No hay productos registrados.");
            } else {
                for (Product p : products) {
                    System.out.println(p.getId() + " | " + p.getName() + " | " + p.getPrice() + "€ | Stock: " + p.getStock());
                }
            }
        } catch (Exception e) {
            System.out.println(" Error al listar productos: " + e.getMessage());
        }
    }

    public void findProductById() {
        try {
            System.out.print("ID del producto: ");
            int id = Integer.parseInt(sc.nextLine());
            Product p = service.getProductById(id);
            if (p != null) {
                System.out.println("ID: " + p.getId());
                System.out.println("Nombre: " + p.getName());
                System.out.println("Precio: " + p.getPrice());
                System.out.println("Categoría: " + p.getCategory());
                System.out.println("Stock: " + p.getStock());
            } else {
                System.out.println(" Producto no encontrado.");
            }
        } catch (Exception e) {
            System.out.println(" Error: ID inválido.");
        }
    }

    public void updateProduct() {
        try {
            System.out.print("ID del producto: ");
            int id = Integer.parseInt(sc.nextLine());
            Product p = service.getProductById(id);
            if (p == null) {
                System.out.println(" Producto no encontrado.");
                return;
            }

            System.out.print("Nuevo Nombre: ");
            String name = sc.nextLine();
            if (!name.isEmpty()) p.setName(name);

            System.out.print("Nuevo Precio: ");
            String priceStr = sc.nextLine();
            if (!priceStr.isEmpty()) p.setPrice(Double.parseDouble(priceStr.replace(",", ".")));

            System.out.print("Nueva Categoría: ");
            String category = sc.nextLine();
            if (!category.isEmpty()) p.setCategory(category);

            System.out.print("Nuevo Stock: ");
            String stockStr = sc.nextLine();
            if (!stockStr.isEmpty()) p.setStock(Integer.parseInt(stockStr));

            service.updateProduct(p);
        } catch (Exception e) {
            System.out.println(" Error al actualizar: " + e.getMessage());
        }
    }

    public void deleteProduct() {
        try {
            System.out.print("ID del producto a eliminar: ");
            int id = Integer.parseInt(sc.nextLine());
            service.deleteProduct(id);
        } catch (Exception e) {
            System.out.println("Error al eliminar: " + e.getMessage());
        }
    }
}