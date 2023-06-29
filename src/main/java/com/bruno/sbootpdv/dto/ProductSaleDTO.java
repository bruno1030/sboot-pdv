package com.bruno.sbootpdv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSaleDTO {

    @NotBlank(message="The sale item is required")
    private long productid;

    @NotBlank(message="The field quantity is required")
    private int quantity;

}
