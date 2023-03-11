package com.bruno.sbootpdv.controller;

import com.bruno.sbootpdv.entity.User;
import com.bruno.sbootpdv.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserRepository repository;

    public UserController(UserRepository repository){
        this.repository = repository;
    }

    @GetMapping()
    public ResponseEntity getAll(){
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody User user){
        try {
            user.setEnable(true);
            return new ResponseEntity<>(repository.save(user), HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity put(@RequestBody User user){
        Optional<User> userToEdit = repository.findById(user.getId());
        if(userToEdit.isPresent()){
            user.setEnable(true);
            return new ResponseEntity<>(repository.save(user), HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        Optional<User> userToDelete = repository.findById(id);
        try{
            repository.deleteById(id);
            return new ResponseEntity<>("Usuario removido com sucesso", HttpStatus.OK);
        }catch(Exception error){
            return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
