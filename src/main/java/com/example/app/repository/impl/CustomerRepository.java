package com.example.app.repository.impl;

import com.example.app.domain.Customer;
import com.example.app.domain.CustomerMapper;
import com.example.app.repository.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerRepository implements AppRepository<Customer> {

    private final JdbcTemplate jdbcTemplate;
    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerRepository(DataSource dataSource, CustomerMapper customerMapper) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.customerMapper = customerMapper;
    }

    @Override
    public boolean create(Customer obj) {
        String SQL = "INSERT INTO customers (name, phone, address) VALUES (?, ?, ?)";
        return jdbcTemplate.update(
                SQL, obj.getName(), obj.getPhone(), obj.getAddress()
        ) > 0;
    }

    @Override
    public Optional<List<Customer>> fetchAll() {
        String SQL = "SELECT * FROM customers";
        Optional<List<Customer>> customersOptional;
        try{
            customersOptional = Optional.of(
                    jdbcTemplate.query(SQL, customerMapper)
            );
        }catch (Exception e){
            customersOptional = Optional.empty();
        }
        return customersOptional;
    }

    @Override
    public Optional<Customer> fetchById(Long id) {
        String SQL = "SELECT * FROM customers WHERE id = ?";
        Optional<Customer> optional;
        try{
            optional = Optional.ofNullable(
                    jdbcTemplate.queryForObject(SQL, customerMapper, id)
            );
        }catch (Exception e){
            optional = Optional.empty();
        }
        return optional;
    }

    @Override
    public boolean update(Long id, Customer obj) {
        String SQL = "UPDATE customers SET name = ?, phone = ?, address = ? WHERE id = ?";
        return jdbcTemplate.update(
                SQL, obj.getName(), obj.getPhone(), obj.getAddress(), id
        ) > 0;
    }

    @Override
    public boolean delete(Long id) {
        String SQL = "DELETE FROM customers WHERE id = ?";
        return jdbcTemplate.update(SQL, id) > 0;
    }

    public Optional<Customer>getLastEntity(){
        String SQL = "SELECT * FROM customers ORDER BY id DESC LIMIT 1";
        Optional<Customer> optional;
        try{
            optional = Optional.ofNullable(jdbcTemplate.queryForObject(
                SQL, customerMapper
            ));
        }catch (Exception e){
            optional = Optional.empty();
        }
        return optional;
    }

    public Optional<List<Customer>> fetchByName(String name) {
        String SQL = "SELECT * FROM customers WHERE name = ?";
        Optional<List<Customer>> optional;
        try{
            optional = Optional.of(jdbcTemplate.query(SQL, customerMapper, name));
        }catch (Exception e){
            optional = Optional.empty();
        }
        return optional;
    }
}
