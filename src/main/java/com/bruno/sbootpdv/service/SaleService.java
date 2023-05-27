package com.bruno.sbootpdv.service;

import com.bruno.sbootpdv.dto.ProductSaleDTO;
import com.bruno.sbootpdv.dto.ProductInfoDTO;
import com.bruno.sbootpdv.dto.SaleDTO;
import com.bruno.sbootpdv.dto.SaleInfoDTO;
import com.bruno.sbootpdv.entity.ItemSale;
import com.bruno.sbootpdv.entity.Product;
import com.bruno.sbootpdv.entity.Sale;
import com.bruno.sbootpdv.entity.User;
import com.bruno.sbootpdv.exception.InvalidOperationException;
import com.bruno.sbootpdv.exception.NoItemException;
import com.bruno.sbootpdv.repository.ItemSaleRepository;
import com.bruno.sbootpdv.repository.ProductRepository;
import com.bruno.sbootpdv.repository.SaleRepository;
import com.bruno.sbootpdv.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;
    private final ItemSaleRepository itemSaleRepository;

    public SaleService(UserRepository userRepository, ProductRepository productRepository, SaleRepository saleRepository, ItemSaleRepository itemSaleRepository){
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.saleRepository = saleRepository;
        this.itemSaleRepository = itemSaleRepository;
    }

    // vai pegar todas as vendas
    public List<SaleInfoDTO> findAll(){
        return saleRepository.findAll().stream().map(sale -> getSaleInfo(sale)).collect(Collectors.toList());
    }

    private SaleInfoDTO getSaleInfo(Sale sale){
        return SaleInfoDTO.builder()
                .user(sale.getUser().getName())
                .date(sale.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .products(getProductInfo(sale.getItems()))
                .build();
    }

    private List<ProductInfoDTO> getProductInfo(List<ItemSale> items){

        if(CollectionUtils.isEmpty(items))
            return Collections.emptyList();

        return items.stream().map(item ->
            ProductInfoDTO.builder()
                    .id(item.getId())
                    .description(item.getProduct().getDescription())
                    .quantity(item.getQuantity())
                    .build()
        ).collect(Collectors.toList());
    }

    // essa anotacao @Transactional eh pra caso eu tenha algum problema no momento de salvar os itens da venda, e nao ficar com lixo na base, com uma venda que nao tem os itens salvos nela
    // entao o Transactional é uma operacao de tudo ou nada
    // se der alguma coisa errada no meio do caminho, ele desfaz tudo que foi feito, faz um rollback
    @Transactional
    public long save(SaleDTO sale){
        User user = userRepository.findById(sale.getUserid())
                .orElseThrow(() -> new NoItemException("Usuario nao encontrado"));

        Sale newSale = new Sale();
        newSale.setUser(user);
        newSale.setDate(LocalDate.now());
        List<ItemSale> items = getItemSale(sale.getItems());

        newSale = saleRepository.save(newSale);

        saveItemSale(items, newSale);

        return newSale.getId();
    }

    private void saveItemSale(List<ItemSale> items, Sale newSale){
        for(ItemSale item: items){
            item.setSale(newSale);
            itemSaleRepository.save(item);
        }
    }

    private List<ItemSale> getItemSale(List<ProductSaleDTO> products){

        if(products.isEmpty()){
            throw new InvalidOperationException("Nao é possivel adicionar a venda sem itens.");
        }

        // O Map transforma uma colecao em outra
        // Entao o trecho abaixo eu uso em vezes de fazer um for, que seria a outra forma de passar em cada product
        // Entao o map vai armazenando todos os meus retornos de "itemSale", e quando acaba ele retorna o objeto "products", que eh nova colecao criada, de Items... isso substitui o for
        return products.stream().map(item -> {
            Product product = productRepository.findById(item.getProductid())
                    .orElseThrow(() -> new NoItemException("Item da venda nao encontrado"));

            ItemSale itemSale = new ItemSale();
            itemSale.setProduct(product);
            itemSale.setQuantity(item.getQuantity());

            if(product.getQuantity() == 0){
                throw new NoItemException("Produto sem estoque");
            }else if(product.getQuantity() < item.getQuantity()){
                throw new InvalidOperationException(String.format("A quantidade de itens da venda (%s) " +
                                                                    "é maior do que a quantidade " +
                                                                    "disponivel no estoque (%s)", item.getQuantity(), product.getQuantity()));
            }

            int total = product.getQuantity() - item.getQuantity();

            product.setQuantity(total);
            productRepository.save(product);

            return itemSale;
        }).collect(Collectors.toList());
    }

    public SaleInfoDTO getById(Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(()-> new NoItemException("Venda nao encontrada"));
        return getSaleInfo(sale);
    }
}
