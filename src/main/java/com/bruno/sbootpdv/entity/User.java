package com.bruno.sbootpdv.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @Column(length = 100, nullable = false)  // esse nullable eh o que vai para o bd, agora see eu quiser antecipar essa validacao e evitar que entre nulo ja no json da chamada, eu utilizo validacao do proprio spring (que eh o spring-boot-starter-validation), a partir da anotacao abaixo @NotNull
    @NotBlank(message="Campo nome eh obrigatorio")
    private String name;

    private boolean isEnable;

    @OneToMany(mappedBy = "user")    // 1 usuario pode ter muitas vendas. // Aqui sao as vendas que esse user efetuou
    private List<Sale> sales;

}
