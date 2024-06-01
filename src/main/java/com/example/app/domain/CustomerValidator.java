package com.example.app.domain;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomerValidator {

    public final static String PHONE_RGX = "[0-9]{3} [0-9]{3}-[0-9]{4}";

    public  Map<String, String> validate(Customer customer) {
        Map<String, String> errors = new HashMap<>();
        if (customer.getName() == null || customer.getName().isEmpty()) {
            errors.put("Name", "has no data");
        }
        if (customer.getAddress() == null || customer.getAddress().isEmpty()) {
            errors.put("Address", "has no data");
        }
        if (customer.getPhone() == null || !customer.getPhone().matches(PHONE_RGX)) {
            errors.put("Phone", "must be in format xxx xxx-xxxx");
        }
        return errors;
    }

}
