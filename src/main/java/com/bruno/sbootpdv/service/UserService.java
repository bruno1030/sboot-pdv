package com.bruno.sbootpdv.service;

import com.bruno.sbootpdv.dto.UserDTO;
import com.bruno.sbootpdv.entity.User;
import com.bruno.sbootpdv.exception.NoItemException;
import com.bruno.sbootpdv.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository repository;

    public UserService(UserRepository repository){
        this.repository = repository;
    }

    public List<UserDTO> getAll(){
        return repository.findAll().stream().map(user ->
                new UserDTO(user.getId(), user.getName(), user.isEnable())
        ).collect(Collectors.toList());     //aqui nessa linha depois do ponto (onde esta collect) eh onde eu digo o que quero retornar
    }

    public UserDTO save(UserDTO userDTO){
        User userToSave = new User();
        userToSave.setEnable(userDTO.isEnabled());
        userToSave.setName(userDTO.getName());
        repository.save(userToSave);    // fazendo isso, o user ja volta do banco de dados com o id setado nele
        return new UserDTO(userToSave.getId(), userToSave.getName(), userToSave.isEnable());
    }

    public UserDTO findById(long id){
        Optional<User> optional = repository.findById(id);

        if(!optional.isPresent()){
            throw new NoItemException("Usuario nao encontrado");
        }
        User user = optional.get();
        return new UserDTO(user.getId(), user.getName(), user.isEnable());
    }

    public UserDTO update(UserDTO userDTO){
        User userToUpdate = new User();
        userToUpdate.setEnable(userDTO.isEnabled());
        userToUpdate.setName(userDTO.getName());
        userToUpdate.setId(userDTO.getId());

        Optional<User> userToEdit = repository.findById(userToUpdate.getId());

        if(!userToEdit.isPresent()){
            throw new NoItemException("Usuario nao encontrado para poder editar");
        }

        repository.save(userToUpdate);
        return new UserDTO(userDTO.getId(), userDTO.getName(), userDTO.isEnabled());
    }

    public void deleteById(long id){
        repository.deleteById(id);
    }

}
