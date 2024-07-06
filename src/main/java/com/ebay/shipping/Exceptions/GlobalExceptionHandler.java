package com.ebay.shipping.Exceptions;

import com.ebay.shipping.Payloads.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({customerIdNotFound.class})
    public ResponseEntity<ApiResponse> idNotFoundExceptionHandler(customerIdNotFound ex){
        String message = ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(message,false);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ emailNotAvailable.class})
    public ResponseEntity<ApiResponse> emailNotFoundExceptionHandler(emailNotAvailable ex){
        String message = ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(message,false);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

}
