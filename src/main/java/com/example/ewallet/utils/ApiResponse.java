package com.example.ewallet.utils;

import lombok.*;

import java.time.ZonedDateTime;

@Data
@Setter
@Getter
@AllArgsConstructor
@Builder
public class ApiResponse {
    private ZonedDateTime timeStamp;
    private int statusCode;
    private String path;
    private  Object data;
    private Boolean isSuccessful;
}
