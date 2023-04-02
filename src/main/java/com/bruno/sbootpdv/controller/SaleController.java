package com.bruno.sbootpdv.controller;

import com.bruno.sbootpdv.dto.SaleDTO;
import com.bruno.sbootpdv.service.SaleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sale")
public class SaleController {

    private SaleService service;

    public SaleController(SaleService service) {
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody SaleDTO sale) {
        try {
            long id = service.save(sale);
            return new ResponseEntity<>("Venda realizada com sucesso - id: " + id, HttpStatus.CREATED);
        } catch (Exception error) {
            return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
