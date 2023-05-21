package com.CredeDataServices.Crede.Data.Services.Task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FileException extends RuntimeException {

    public FileException(IOException e) {
        super(e);
    }
}
