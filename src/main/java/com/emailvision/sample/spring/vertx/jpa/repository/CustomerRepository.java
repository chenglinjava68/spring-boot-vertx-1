package com.emailvision.sample.spring.vertx.jpa.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.emailvision.sample.spring.vertx.jpa.domain.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByLastName(String lastName);
}