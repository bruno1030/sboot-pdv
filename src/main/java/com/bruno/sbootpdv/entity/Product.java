package com.bruno.sbootpdv.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String description;

    @Column(length = 20, precision = 20, scale = 2)
    // precision eh quantos numeros eu posso digitar nesse campo, ja somando com os campos depois da virgula, scale eh a quantidade de casas decimais depois da virgula
    private BigDecimal price;

    @Column(nullable = true)
    private int quantity;

}
