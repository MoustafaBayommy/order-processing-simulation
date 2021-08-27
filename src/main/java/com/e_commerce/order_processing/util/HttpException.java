package com.e_commerce.order_processing.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class HttpException extends ResponseStatusException {

    public HttpException(HttpStatus HttpStatus,String message){
        super(HttpStatus, message);
    }
}
