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
        if (c.getName() == null || c.getName().trim().isEmpty()) {
            System.out.println(" Error: El nombre no puede estar vacío");
            return;
        }
        customerRepository.create(c);
    }

    public Customer getCustomerById(int id) {
        return customerRepository.findById(id);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public void updateCustomer(Customer c) {
        if (c == null || c.getId() <= 0) {
            System.out.println(" Error: Datos inválidos para actualizar");
            return;
        }
        customerRepository.update(c);
    }

    public boolean deleteCustomer(int id) {
        try {
            // Verificar si tiene pedidos
            List<Order> pedidos = orderRepository.findAll();
            boolean tienePedidos = pedidos.stream().anyMatch(o -> o.getCustomerId() == id);

            if (tienePedidos) {
                System.out.println(" No se puede eliminar el cliente.");
                System.out.println("   Tiene pedidos asociados. Elimine primero los pedidos.");
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