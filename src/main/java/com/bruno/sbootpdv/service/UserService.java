package com.bruno.sbootpdv.service;

import com.bruno.sbootpdv.dto.UserDTO;
import com.bruno.sbootpdv.dto.UserResponseDTO;
import com.bruno.sbootpdv.entity.User;
import com.bruno.sbootpdv.exception.NoItemException;
import com.bruno.sbootpdv.repository.UserRepository;
import com.bruno.sbootpdv.security.SecurityConfig;
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

    public List<UserResponseDTO> getAll() {
        return repository.findAll().stream().map(user ->
                new UserResponseDTO(user.getId(), user.getName(), user.isEnable(),user.getUsername())
        ).collect(Collectors.toList());     //aqui nessa linha depois do ponto (onde esta collect) eh onde eu digo o que quero retornar
    }

    public UserResponseDTO save(UserDTO userDTO) {
        userDTO.setPassword(SecurityConfig.passwordEncoder().encode(userDTO.getPassword()));
        User userToSave = mapper.map(userDTO, User.class);
        repository.save(userToSave);    // passando o userToSave para o repository, quando ele voltar do banco de dados, ele ja volta com o id setado nele pra eu utilizar na linha abaixo
        return new UserResponseDTO(userToSave.getId(), userToSave.getName(), userToSave.isEnable(), userToSave.getUsername());
    }

    public UserResponseDTO findById(long id) {
        Optional<User> optional = repository.findById(id);

        if (!optional.isPresent()) {
            throw new NoItemException("Usuario nao encontrado");
        }
        User user = optional.get();
        return new UserResponseDTO(user.getId(), user.getName(), user.isEnable(), user.getUsername());
    }

    public UserDTO update(UserDTO userDTO) {
        userDTO.setPassword(SecurityConfig.passwordEncoder().encode(userDTO.getPassword()));
        User userToUpdate = mapper.map(userDTO, User.class);

        Optional<User> userToEdit = repository.findById(userToUpdate.getId());

        if (!userToEdit.isPresent()) {
            throw new NoItemException("Usuario nao encontrado para poder editar");
        }

        repository.save(userToUpdate);
        return new UserDTO(userDTO.getId(), userDTO.getName(), userDTO.isEnabled(), userDTO.getUsername(), userDTO.getPassword());
    }

    public void deleteById(long id) {
        repository.deleteById(id);
    }

}
