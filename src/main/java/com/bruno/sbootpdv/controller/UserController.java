package com.bruno.sbootpdv.controller;

import com.bruno.sbootpdv.dto.ResponseDTO;
import com.bruno.sbootpdv.dto.UserDTO;
import com.bruno.sbootpdv.entity.User;
import com.bruno.sbootpdv.service.UserService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity post(@RequestBody User user) {
        try {
            user.setEnable(true);
            return new ResponseEntity<>(service.save(user), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity put(@RequestBody User user) {
        try {
            return new ResponseEntity(service.update(user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        UserDTO userToDelete = service.findById(id);
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
