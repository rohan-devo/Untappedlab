package com.example.shopping.basket.controller;

import com.example.shopping.basket.model.AddProductsResponse;
import com.example.shopping.basket.model.Product;
import com.example.shopping.basket.model.Receipt;
import com.example.shopping.basket.service.ShoppingBasketService;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/baskets")
public class ShoppingBasketController {

    @Autowired
    ShoppingBasketService shoppingBasketService;

    @PostMapping("/products")
    public AddProductsResponse addProductsToBasket(@RequestBody List<Product> products) {
        Objects.requireNonNull(products, "product cannot be null");
        return shoppingBasketService.addToBasket(products);
    }

    @GetMapping("/{basketId}/receipt")
    public Receipt generateReceipt(@PathVariable UUID basketId) {
        return shoppingBasketService.generateReceipt(basketId);
    }
}
