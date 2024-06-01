package com.example.app.service;

import com.example.app.domain.Customer;
import com.example.app.domain.CustomerValidator;
import com.example.app.exceptions.CustomerDataException;
import com.example.app.network.*;
import com.example.app.repository.impl.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerValidator validator;

    @Autowired
    public CustomerService(CustomerRepository repository, CustomerValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public String create(Customer customer) {
        Map<String, String> errors = validator.validate(customer);
        if (!errors.isEmpty()) {
            try {
                throw new CustomerDataException("Check inputs", errors);
            }catch (CustomerDataException e) {
                return new ResponseUtil<ResponseInfo>()
                        .getResponse(new ResponseInfo(HttpStatus.UNPROCESSABLE_ENTITY.toString(),
                                false, e.getErrors(errors)));
            }
        }
        Optional<Customer> optional;
        if (repository.create(customer)) {
            optional = repository.getLastEntity();
            if (optional.isPresent()) {
                Customer createdCustomer = optional.get();
                return new ResponseUtil<ResponseData<Customer>>()
                        .getResponse(new ResponseData<>(HttpStatus.CREATED.toString(),
                                true, createdCustomer));
            }
            return new ResponseUtil<ResponseInfo>()
                    .getResponse(new ResponseInfo(HttpStatus.NOT_FOUND.toString(),
                            false, ResponseMessage.SMTH_WRONG.getResponseMsg()));
        }
        return new ResponseUtil<ResponseInfo>()
                .getResponse(new ResponseInfo(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                        false, ResponseMessage.SMTH_WRONG.getResponseMsg()));
    }

    public String fetchAll() {
        Optional<List<Customer>> optional = repository.fetchAll();
        if (optional.isPresent()) {
            List<Customer> customers = optional.get();
            return new ResponseUtil<ResponseDataList<Customer>>()
                    .getResponse(new ResponseDataList<>(HttpStatus.OK.toString(),
                            true, customers));
        } else return new ResponseUtil<ResponseInfo>()
                .getResponse(new ResponseInfo(HttpStatus.NOT_FOUND.toString(),
                        false, ResponseMessage.SMTH_WRONG.getResponseMsg()));
    }

    public String fetchById(Long id) {
        Optional<Customer> optional = repository.fetchById(id);
        if (optional.isPresent()) {
            Customer customer = optional.get();
            return new ResponseUtil<ResponseData<Customer>>()
                    .getResponse(new ResponseData<>(HttpStatus.OK.toString(),
                            true, customer));
        }else {
            return new ResponseUtil<ResponseInfo>()
                    .getResponse(new ResponseInfo(HttpStatus.NOT_FOUND.toString(),
                            false, ResponseMessage.SMTH_WRONG.getResponseMsg()));
        }
    }

    public String update(Long id, Customer customer) {
        Optional<Customer> optional = repository.fetchById(id);
        if (optional.isPresent()) {
            Map<String, String> errors = validator.validate(customer);
            if (!errors.isEmpty()) {
                try {
                    throw new CustomerDataException("Check inputs", errors);
                }catch (CustomerDataException e){
                    return new ResponseUtil<ResponseInfo>()
                            .getResponse(new ResponseInfo(HttpStatus.UNPROCESSABLE_ENTITY.toString(),
                                    false, e.getErrors(errors)));
                }
            }
            if (repository.update(id, customer)) {
                Optional<Customer> updatedCustomerOptional = repository.fetchById(id);
                if (updatedCustomerOptional.isPresent()) {
                    Customer updatedCustomer = updatedCustomerOptional.get();
                    return new ResponseUtil<ResponseData<Customer>>()
                            .getResponse(new ResponseData<>(HttpStatus.OK.toString(),
                                    true, updatedCustomer));
                }
                return new ResponseUtil<ResponseInfo>()
                        .getResponse(new ResponseInfo(HttpStatus.NOT_FOUND.toString(),
                                false, ResponseMessage.SMTH_WRONG.getResponseMsg()));
            }
            return new ResponseUtil<ResponseInfo>()
                    .getResponse(new ResponseInfo(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                            false, ResponseMessage.SMTH_WRONG.getResponseMsg()));
        }
        return new ResponseUtil<ResponseInfo>()
                .getResponse(new ResponseInfo(HttpStatus.NOT_FOUND.toString(),
                        false, ResponseMessage.SMTH_WRONG.getResponseMsg()));
    }

    public String delete(Long id) {
        if (repository.fetchById(id).isPresent()) {
            if (repository.delete(id)) {
                return new ResponseUtil<ResponseInfo>()
                        .getResponse(new ResponseInfo(HttpStatus.OK.toString(),
                                true, ResponseMessage.DELETED.getResponseMsg()));
            }
            return new ResponseUtil<ResponseInfo>()
                    .getResponse(new ResponseInfo(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                            false, ResponseMessage.SMTH_WRONG.getResponseMsg()));
        }
        return new ResponseUtil<ResponseInfo>()
                .getResponse(new ResponseInfo(HttpStatus.NOT_FOUND.toString(),
                        false, ResponseMessage.SMTH_WRONG.getResponseMsg()));
    }

    public String getLastEntity(){
        Optional<Customer> optional = repository.getLastEntity();
        if (optional.isPresent()) {
            Customer customer = optional.get();
            return new ResponseUtil<ResponseData<Customer>>()
                    .getResponse(new ResponseData<>(HttpStatus.OK.toString(),
                            true, customer));
        }
        return new ResponseUtil<ResponseInfo>()
                .getResponse(new ResponseInfo(HttpStatus.NOT_FOUND.toString(),
                        false, ResponseMessage.SMTH_WRONG.getResponseMsg()));
    }

    public String fetchAllByName(String name) {
        Optional<List<Customer>> optional = repository.fetchByName(name);
        if (optional.isPresent()) {
            List<Customer> customers = optional.get();
            if (!customers.isEmpty()){
                return new ResponseUtil<ResponseDataList<Customer>>()
                        .getResponse(new ResponseDataList<>(HttpStatus.OK.toString(),
                                true, customers));
            }
        }
        return new ResponseUtil<ResponseInfo>()
                .getResponse(new ResponseInfo(HttpStatus.NOT_FOUND.toString(),
                        false, ResponseMessage.SMTH_WRONG.getResponseMsg()));
    }
}
