package com.example.app.network;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseInfo {
    private String status;
    private boolean success;
    private String message;
}
