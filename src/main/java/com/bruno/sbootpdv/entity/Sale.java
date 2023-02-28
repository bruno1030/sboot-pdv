package com.bruno.sbootpdv.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "sale")
@Entity
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sale_date", nullable = false)
    private LocalDate date;    // data que a venda foi realizada

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 1 venda pode ter muitos itens
    // mappedBy eh pra eu dizer qual o nome do atributo que mapeou essa classe Sale la na classe ItemSale.
    // FetchType.LAZY eh pq quando eu for obter uma venda eu nao quero que venha os itens juntos por padrao. Se eu quiser que venha os itens da venda, ai eu vou ter um methodo especifico, um fetch especifico pra isso. Serao 2 endpoints distintos, por tanto
    @OneToMany(mappedBy = "sale", fetch = FetchType.LAZY)
    private List<ItemSale> items;

}
