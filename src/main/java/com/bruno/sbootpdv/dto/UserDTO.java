package com.bruno.sbootpdv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    public UserDTO(Long id, String name, boolean isEnabled, String username){
        this.id = id;
        this.name = name;
        this.isEnabled = isEnabled;
        this.username = username;
    }

    private Long id;
    private String name;
    private boolean isEnabled;
    private String username;
    private String password;

}
