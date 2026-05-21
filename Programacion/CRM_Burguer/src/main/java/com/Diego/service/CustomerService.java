package com.Diego.service;

import com.Diego.model.Customer;
import com.Diego.repository.CustomerRepository;

public class CustomerService {

    private CustomerRepository repository =
            new CustomerRepository();

    public void addCustomer(Customer c){
        repository.create(c);
    }
}