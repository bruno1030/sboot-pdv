package com.bruno.sbootpdv.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "product")
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    @NotBlank(message="O campo descricao eh obrigatorio")
    private String description;

    @Column(length = 20, precision = 20, scale = 2)
    // precision eh quantos numeros eu posso digitar nesse campo, ja somando com os campos depois da virgula, scale eh a quantidade de casas decimais depois da virgula
    @NotNull(message="O campo price eh obrigatorio")
    private BigDecimal price;

    @Column(nullable = true)
    @NotNull(message="O campo quantity eh obrigatorio")
    @Min(1)
    private int quantity;

}
