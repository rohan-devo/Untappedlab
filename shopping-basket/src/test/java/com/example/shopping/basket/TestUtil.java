package com.example.shopping.basket;

import com.example.shopping.basket.model.Product;
import com.example.shopping.basket.model.ProductType;
import java.util.ArrayList;
import java.util.List;

public class TestUtil {

    private TestUtil() {

    }

    public static List<Product> createProducts(int count) {
        var products = new ArrayList<Product>();
        for (int i = 0; i < count; ++i) {
            products.add(createProduct("test " + count, 10.0, 1, ProductType.FOOD, false));
        }
        return products;
    }

    public static Product createProduct(String name, double price, int quantity, ProductType type, boolean isImported) {
        return Product.builder()
                .name(name)
                .price(price)
                .quantity(quantity)
                .type(type)
                .isImported(isImported)
                .build();
    }
}