package com.bruno.sbootpdv.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    private boolean isEnable;

    @OneToMany(mappedBy = "user")    // 1 usuario pode ter muitas vendas. // Aqui sao as vendas que esse user efetuou
    private List<Sale> sales;

}
