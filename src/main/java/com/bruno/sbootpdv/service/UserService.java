package com.bruno.sbootpdv.service;

import com.bruno.sbootpdv.dto.UserDTO;
import com.bruno.sbootpdv.entity.User;
import com.bruno.sbootpdv.exception.NoItemException;
import com.bruno.sbootpdv.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository repository;

    private ModelMapper mapper;

    public UserService(UserRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<UserDTO> getAll() {
        return repository.findAll().stream().map(user ->
                new UserDTO(user.getId(), user.getName(), user.isEnable())
        ).collect(Collectors.toList());     //aqui nessa linha depois do ponto (onde esta collect) eh onde eu digo o que quero retornar
    }

    public UserDTO save(UserDTO userDTO) {
        User userToSave = mapper.map(userDTO, User.class);
        repository.save(userToSave);    // passando o userToSave para o repository, quando ele voltar do banco de dados, ele ja volta com o id setado nele pra eu utilizar na linha abaixo
        return new UserDTO(userToSave.getId(), userToSave.getName(), userToSave.isEnable());
    }

    public UserDTO findById(long id) {
        Optional<User> optional = repository.findById(id);

        if (!optional.isPresent()) {
            throw new NoItemException("Usuario nao encontrado");
        }
        User user = optional.get();
        return new UserDTO(user.getId(), user.getName(), user.isEnable());
    }

    public UserDTO update(UserDTO userDTO) {
        User userToUpdate = mapper.map(userDTO, User.class);

        Optional<User> userToEdit = repository.findById(userToUpdate.getId());

        if (!userToEdit.isPresent()) {
            throw new NoItemException("Usuario nao encontrado para poder editar");
        }

        repository.save(userToUpdate);
        return new UserDTO(userDTO.getId(), userDTO.getName(), userDTO.isEnabled());
    }

    public void deleteById(long id) {
        repository.deleteById(id);
    }

}
