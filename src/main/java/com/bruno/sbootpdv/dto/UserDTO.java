package com.bruno.sbootpdv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    @NotBlank(message="The field name is required")
    private String name;

    private boolean isEnabled;

    @NotBlank(message = "The field username is required")
    private String username;

    @NotBlank(message = "The field password is required")
    private String password;

}
