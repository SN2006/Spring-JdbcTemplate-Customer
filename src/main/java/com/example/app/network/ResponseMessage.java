package com.example.app.network;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {

    DELETED("Deleted."),
    SMTH_WRONG("Something wrong."); // <- повинно бути ; !

    private final String responseMsg;
}
