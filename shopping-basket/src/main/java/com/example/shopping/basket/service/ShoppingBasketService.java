package com.example.shopping.basket.service;

import com.example.shopping.basket.model.AddProductsResponse;
import com.example.shopping.basket.model.Product;
import com.example.shopping.basket.model.Receipt;
import java.util.List;
import java.util.UUID;

public interface ShoppingBasketService {

    AddProductsResponse addToBasket(List<Product> products);

    Receipt generateReceipt(UUID basketId);
}
