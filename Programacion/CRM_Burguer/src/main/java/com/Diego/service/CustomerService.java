package com.Diego.service;

import com.Diego.model.Customer;
import com.Diego.repository.CustomerRepository;
import java.util.List;

public class CustomerService {

    private CustomerRepository repository = new CustomerRepository();

    public void addCustomer(Customer c) {
        repository.create(c);
    }

    public Customer getCustomerById(int id) {
        return repository.findById(id);
    }

    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

    public void updateCustomer(Customer c) {
        repository.update(c);
    }

    public void deleteCustomer(int id) {
        repository.delete(id);
    }
}