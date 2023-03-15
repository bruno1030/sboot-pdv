package com.bruno.sbootpdv.controller;

import com.bruno.sbootpdv.dto.SaleDTO;
import com.bruno.sbootpdv.repository.SaleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sale")
public class SaleController {

    private SaleRepository repository;

    @PostMapping
    public ResponseEntity post(@ResponseBody SaleDTO sale){

    }

}
