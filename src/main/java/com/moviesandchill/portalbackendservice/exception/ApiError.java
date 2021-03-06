package com.moviesandchill.portalbackendservice.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiError {
    private String message;
    private String dateTime = LocalDateTime.now().toString(); // HACK

    public ApiError(Exception exception) {
        message = exception.getMessage();
    }

    public ApiError(String message) {
        this.message = message;
    }
}
