package com.example.app.network;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ResponseUtil<T> {

    public String getResponse(T response) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(response);
        } catch(Exception e) {
            return "{\"status\" : \"500 Internal Server Error\", " +
                    "\"success\" : false, " +
                    "\"message\" : \"Something went wrong\"" +
                    "}";
        }
    }
}
