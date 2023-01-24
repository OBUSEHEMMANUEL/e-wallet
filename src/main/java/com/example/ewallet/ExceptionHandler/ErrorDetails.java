package com.example.ewallet.ExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
@Setter
public class ErrorDetails {
    private ZonedDateTime timeStamp;
    private String message;
    private String description;
}
