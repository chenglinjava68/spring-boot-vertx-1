package com.emailvision.sample.spring.vertx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emailvision.sample.spring.vertx.jpa.domain.Customer;
import com.emailvision.sample.spring.vertx.jpa.repository.CustomerRepository;


@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    public Iterable<Customer> findAll() {
        return repository.findAll();
    }

    public Customer save() {
        return repository.save(new Customer("toto", "tata"));
    }
}
