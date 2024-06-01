package com.example.app.controller;

import com.example.app.domain.Customer;
import com.example.app.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerService service;

    @Autowired
    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping
    public String findAllCustomer() {
        return service.fetchAll();
    }

    @GetMapping("/{id}")
    public String findCustomerById(@PathVariable("id") Long id) {
        return service.fetchById(id);
    }

    @GetMapping("/query-by-name")
    public String findCustomerByName(@RequestParam("name") String name) {
        return service.fetchAllByName(name);
    }

    @GetMapping("/last-entity")
    public String findLastEntity() {
        return service.getLastEntity();
    }

    @PostMapping
    public String createCustomer(@RequestBody Customer customer) {
        return service.create(customer);
    }

    @PutMapping("/{id}")
    public String updateCustomer(@PathVariable("id") Long id, @RequestBody Customer customer) {
        return service.update(id, customer);
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable("id") Long id) {
        return service.delete(id);
    }
}
