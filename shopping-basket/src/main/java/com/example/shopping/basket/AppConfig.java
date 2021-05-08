package com.example.shopping.basket;

import com.example.shopping.basket.repository.ShoppingBasketJPARepository;
import com.example.shopping.basket.service.ShoppingBasketService;
import com.example.shopping.basket.service.impl.ShoppingBasketServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ShoppingBasketService configureShoppingBasketService(
            final ShoppingBasketJPARepository shoppingBasketRepository,
            @Value("${shopping.basket.sales-tax}") final double salesTax,
            @Value("${shopping.basket.import-tax}") final double importTax) {
        return new ShoppingBasketServiceImpl(shoppingBasketRepository, salesTax, importTax);
    }
}
