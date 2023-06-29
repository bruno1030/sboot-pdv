package com.bruno.sbootpdv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;

    @NotBlank(message="The field description is required")
    private String description;

    @NotBlank(message="The field price is required")
    private BigDecimal price;

    @NotBlank(message="The field quantity is required")
    private int quantity;

}
