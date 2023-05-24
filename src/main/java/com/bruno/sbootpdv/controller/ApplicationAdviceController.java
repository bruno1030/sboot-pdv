package com.bruno.sbootpdv.controller;

import com.bruno.sbootpdv.dto.ResponseDTO;
import com.bruno.sbootpdv.exception.InvalidOperationException;
import com.bruno.sbootpdv.exception.NoItemException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationAdviceController {

    @ExceptionHandler(NoItemException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO handlerNoItemException(NoItemException ex){
        String messageError = ex.getMessage();
        return new ResponseDTO(messageError);
    }

    @ExceptionHandler(InvalidOperationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO handlerInvalidOperationException(InvalidOperationException ex){
        String messageError = ex.getMessage();
        return new ResponseDTO(messageError);
    }

}
