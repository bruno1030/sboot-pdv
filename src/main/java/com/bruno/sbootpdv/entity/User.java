package com.bruno.sbootpdv.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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
    @NotBlank(message="The field name is required")
    private String name;

    @Column(length = 30, nullable = false, unique = true)
    @NotBlank(message = "The field username is required")
    private String username;

    @Column(length = 70, nullable = false, columnDefinition="text")
    @NotBlank(message = "The field password is required")
    private String password;

    private boolean isEnable;

    @OneToMany(mappedBy = "user")    // 1 user can have many sales. // Aqui sao as vendas que esse user efetuou
    private List<Sale> sales;

}
