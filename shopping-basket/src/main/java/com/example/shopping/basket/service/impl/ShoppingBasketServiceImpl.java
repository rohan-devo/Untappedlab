package com.example.shopping.basket.service.impl;

import com.example.shopping.basket.exception.BasketDoesNotExistException;
import com.example.shopping.basket.model.*;
import com.example.shopping.basket.repository.ShoppingBasketJPARepository;
import com.example.shopping.basket.service.ShoppingBasketService;
import com.example.shopping.basket.util.AmountUtil;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ShoppingBasketServiceImpl implements ShoppingBasketService {

    private final ShoppingBasketJPARepository shoppingBasketRepository;

    private final double SALES_TAX;

    private final double IMPORT_TAX;

    public ShoppingBasketServiceImpl(final ShoppingBasketJPARepository shoppingBasketRepository,
            final double salesTax, final double importTax) {
        Objects.requireNonNull(shoppingBasketRepository, "shoppingBasketRepository cannot be null");
        this.shoppingBasketRepository = shoppingBasketRepository;
        this.SALES_TAX = salesTax;
        this.IMPORT_TAX = importTax;
    }

    @Override
    public AddProductsResponse addToBasket(List<Product> products) {
        Objects.requireNonNull(products, "products cannot be null");

        var basketId = UUID.randomUUID();
        products.forEach(product -> product.setBasketId(basketId));

        shoppingBasketRepository.saveAll(products);

        return AddProductsResponse.builder()
                .basketId(basketId)
                .build();
    }

    @Override
    public Receipt generateReceipt(UUID basketId) {
        Objects.requireNonNull(basketId, "basketId cannot be null");

        var allProducts = shoppingBasketRepository.findByBasketId(basketId);
        var orderItems = new ArrayList<OrderItem>();

        if (allProducts == null || allProducts.isEmpty())
            throw new BasketDoesNotExistException(basketId);

        for (Product product : allProducts) {
            var orderItem = OrderItem.builder()
                    .name(product.getName())
                    .quantity(product.getQuantity())
                    .price(product.getPrice() * product.getQuantity())
                    .build();
            if (product.getType().equals(ProductType.OTHER)) {
                orderItem.setTax(AmountUtil.roundNumberToDecimalPlaces(orderItem.getPrice() * SALES_TAX, 2));
            }

            if (product.isImported()) {
                orderItem.setTax(AmountUtil.roundNumberToDecimalPlaces(
                        orderItem.getTax() + orderItem.getPrice() * IMPORT_TAX, 2));
            }

            orderItem.setPrice(AmountUtil.roundOffAmount(orderItem.getPrice() + orderItem.getTax()));

            orderItems.add(orderItem);
        }

        double totalSalesTax = 0;
        double totalAmount = 0;

        for (OrderItem item : orderItems) {
            totalSalesTax += item.getTax();
            totalAmount += item.getPrice();
        }

        return Receipt.builder()
                .orderItems(orderItems)
                .salesTaxes(totalSalesTax)
                .total(totalAmount)
                .timestamp(Instant.now())
                .build();
    }
}
