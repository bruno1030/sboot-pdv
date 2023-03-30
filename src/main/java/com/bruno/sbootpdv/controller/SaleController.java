package com.bruno.sbootpdv.controller;

import com.bruno.sbootpdv.dto.SaleDTO;
import com.bruno.sbootpdv.repository.SaleRepository;
import com.bruno.sbootpdv.service.SaleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sale")
public class SaleController {

    private SaleRepository repository;
    private SaleService service;

    @PostMapping
    public ResponseEntity post(@ResponseBody SaleDTO sale){
        return new ResponseEntity<>(service.save(sale), HttpStatus.CREATED);
    }

}
