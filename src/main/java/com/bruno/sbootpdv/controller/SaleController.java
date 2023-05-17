package com.bruno.sbootpdv.controller;

import com.bruno.sbootpdv.dto.ResponseDTO;
import com.bruno.sbootpdv.dto.SaleDTO;
import com.bruno.sbootpdv.dto.SaleInfoDTO;
import com.bruno.sbootpdv.exception.InvalidOperationException;
import com.bruno.sbootpdv.exception.NoItemException;
import com.bruno.sbootpdv.service.SaleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/sale")
public class SaleController {

    private SaleService service;

    public SaleController(SaleService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity getAll(){

        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity getById(@PathVariable Long id){
        try{
            return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
        } catch(NoItemException | InvalidOperationException error){
            return new ResponseEntity<>(new ResponseDTO(error.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody SaleDTO sale) {
        try {
            long id = service.save(sale);
            return new ResponseEntity<>(new ResponseDTO("Venda realizada com sucesso!"), HttpStatus.CREATED);
        } catch(NoItemException | InvalidOperationException error){
            return new ResponseEntity<>(new ResponseDTO(error.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception error) {
            return new ResponseEntity<>(new ResponseDTO(error.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
