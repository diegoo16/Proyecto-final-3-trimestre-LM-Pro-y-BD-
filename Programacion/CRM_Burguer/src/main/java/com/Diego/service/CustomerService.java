package com.Diego.service;

import com.Diego.model.Customer;
import com.Diego.model.Order;
import com.Diego.repository.CustomerRepository;
import com.Diego.repository.OrderRepository;
import java.util.List;

public class CustomerService {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    public CustomerService() {
        this.customerRepository = new CustomerRepository();
        this.orderRepository = new OrderRepository();
    }

    public void addCustomer(Customer c) {
        if (c == null) {
            System.out.println(" Error: Cliente no puede ser nulo.");
            return;
        }

        // Validación de Nombre
        if (c.getName() == null || c.getName().trim().isEmpty()) {
            System.out.println(" Error: El nombre es obligatorio.");
            return;
        }

        // Validación de Teléfono (7 a 9 caracteres)
        if (c.getPhone() == null || c.getPhone().trim().isEmpty()) {
            System.out.println(" Error: El teléfono es obligatorio.");
            return;
        }
        String phoneClean = c.getPhone().trim();
        if (phoneClean.length() < 7 || phoneClean.length() > 9) {
            System.out.println(" Error: El teléfono debe tener entre 7 y 9 caracteres.");
            return;
        }

        // Validación de Email (debe contener @)
        if (c.getEmail() == null || c.getEmail().trim().isEmpty()) {
            System.out.println(" Error: El email es obligatorio.");
            return;
        }
        if (!c.getEmail().contains("@")) {
            System.out.println(" Error: El email debe contener un '@'.");
            return;
        }

        customerRepository.create(c);
    }

    public Customer getCustomerById(int id) {
        if (id <= 0) {
            System.out.println(" Error: ID inválido.");
            return null;
        }
        return customerRepository.findById(id);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public void updateCustomer(Customer c) {
        if (c == null || c.getId() <= 0) {
            System.out.println(" Error: Datos inválidos para actualizar.");
            return;
        }
        customerRepository.update(c);
    }

    public boolean deleteCustomer(int id) {
        if (id <= 0) {
            System.out.println(" Error: ID inválido.");
            return false;
        }

        try {
            List<Order> pedidos = orderRepository.findAll();
            boolean tienePedidos = pedidos.stream().anyMatch(o -> o.getCustomerId() == id);

            if (tienePedidos) {
                System.out.println(" No se puede eliminar el cliente.");
                System.out.println("Tiene pedidos asociados.");
                return false;
            }

            customerRepository.delete(id);
            System.out.println(" Cliente eliminado correctamente.");
            return true;

        } catch (Exception e) {
            System.out.println(" Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }
}