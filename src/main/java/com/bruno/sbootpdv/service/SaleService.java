package com.bruno.sbootpdv.service;

import com.bruno.sbootpdv.dto.ProductDTO;
import com.bruno.sbootpdv.dto.SaleDTO;
import com.bruno.sbootpdv.dto.SaleInfoDTO;
import com.bruno.sbootpdv.entity.ItemSale;
import com.bruno.sbootpdv.entity.Product;
import com.bruno.sbootpdv.entity.Sale;
import com.bruno.sbootpdv.entity.User;
import com.bruno.sbootpdv.repository.ItemSaleRepository;
import com.bruno.sbootpdv.repository.ProductRepository;
import com.bruno.sbootpdv.repository.SaleRepository;
import com.bruno.sbootpdv.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    }

    // essa anotacao @Transactional eh pra caso eu tenha algum problema no momento de salvar os itens da venda, e nao ficar com lixo na base, com uma venda que nao tem os itens salvos nela
    // entao o Transactional é uma operacao de tudo ou nada
    // se der alguma coisa errada no meio do caminho, ele desfaz tudo que foi feito, faz um rollback
    @Transactional
    public long save(SaleDTO sale){

        Optional<User> user = userRepository.findById(sale.getUserid());

        Sale newSale = new Sale();
        newSale.setUser(user.get());
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

    private List<ItemSale> getItemSale(List<ProductDTO> products){
        // O Map transforma uma colecao em outra
        // Entao o trecho abaixo eu uso em vezes de fazer um for, que seria a outra forma de passar em cada product
        // Entao o map vai armazenando todos os meus retornos de "itemSale", e quando acaba ele retorna o objeto "products", que eh nova colecao criada, de Items... isso substitui o for
        return products.stream().map(item -> {
            Product product = productRepository.getReferenceById(item.getProductid());

            ItemSale itemSale = new ItemSale();
            itemSale.setProduct(product);
            itemSale.setQuantity(item.getQuantity());

            return itemSale;
        }).collect(Collectors.toList());
    }

}
