package com.bruno.sbootpdv.controller;

import com.bruno.sbootpdv.dto.ResponseDTO;
import com.bruno.sbootpdv.dto.UserDTO;
import com.bruno.sbootpdv.dto.UserResponseDTO;
import com.bruno.sbootpdv.service.UserService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity post(@Valid @RequestBody UserDTO user) {
        try {
            user.setEnabled(true);
            return new ResponseEntity<>(service.save(user), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity put(@Valid @RequestBody UserDTO user) {
        try {
            return new ResponseEntity(service.update(user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        UserResponseDTO userToDelete = service.findById(id);
        try {
            service.deleteById(id);
            return new ResponseEntity<>(new ResponseDTO("User removed succesfully"), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(new ResponseDTO("Nao foi possivel localizar o usuario"), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
