package com.example.app.network;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseData<T> {
    private String status;
    private boolean success;
    private T data;
}
