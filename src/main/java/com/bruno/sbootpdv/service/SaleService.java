package com.bruno.sbootpdv.service;

import com.bruno.sbootpdv.dto.ProductDTO;
import com.bruno.sbootpdv.dto.SaleDTO;
import com.bruno.sbootpdv.entity.ItemSale;
import com.bruno.sbootpdv.entity.Sale;
import com.bruno.sbootpdv.entity.User;
import com.bruno.sbootpdv.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SaleService {

    private final UserRepository userRepository;

    public SaleService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public long save(SaleDTO sale){

        Optional<User> user = userRepository.findById(sale.getUserid());

        Sale newSale = new Sale();
        newSale.setUser(user.get());
        newSale.setDate(LocalDate.now());
        newSale.setItems();
    }

    private List<ItemSale> getItemSale(List<ProductDTO> products){

    }

}
