package com.example.shopping.basket.service.impl;

import com.example.shopping.basket.model.ProductType;
import com.example.shopping.basket.repository.ShoppingBasketJPARepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.shopping.basket.TestUtil.createProducts;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShoppingBasketServiceImplTest {

    private ShoppingBasketServiceImpl shoppingBasketService;

    @Mock
    private ShoppingBasketJPARepository shoppingBasketRepository;

    @BeforeEach
    public void setup() {
        shoppingBasketService = new ShoppingBasketServiceImpl(shoppingBasketRepository, 0.1, 0.05);
    }

    @Test
    public void testAddProduct() {
        shoppingBasketService.addToBasket(createProducts(1));

        verify(shoppingBasketRepository, atMostOnce()).saveAll(anyList());
    }

    @Test
    public void testGenerateReceiptWithImportedItems() {
        var products = createProducts(1);
        products.forEach(product -> product.setImported(true));

        when(shoppingBasketRepository.findByBasketId(any())).thenReturn(products);

        var response = shoppingBasketService.addToBasket(products);

        var receipt = shoppingBasketService.generateReceipt(response.getBasketId());

        assertEquals(1, receipt.getOrderItems().size());
        assertEquals(10.5, receipt.getTotal());
        assertEquals(0.5, receipt.getSalesTaxes());
    }

    @Test
    public void testGenerateReceiptWithoutImportedItems() {
        var products = createProducts(1);
        when(shoppingBasketRepository.findByBasketId(any())).thenReturn(products);

        var response = shoppingBasketService.addToBasket(products);

        var receipt = shoppingBasketService.generateReceipt(response.getBasketId());
        assertEquals(1, receipt.getOrderItems().size());
        assertEquals(10.0, receipt.getTotal());
        assertEquals(0.0, receipt.getSalesTaxes());
    }

    @Test
    public void testGenerateReceiptWithOtherType() {
        var products = createProducts(1);
        products.forEach(product -> product.setType(ProductType.OTHER));

        when(shoppingBasketRepository.findByBasketId(any())).thenReturn(products);

        var response = shoppingBasketService.addToBasket(products);

        var receipt = shoppingBasketService.generateReceipt(response.getBasketId());
        assertEquals(1, receipt.getOrderItems().size());
        receipt.getOrderItems()
                .forEach(item -> {
                    assertEquals(item.getName(), "test 1");
                    assertEquals(item.getQuantity(), 1);
                    assertEquals(item.getPrice(), 11.0);
                    assertEquals(item.getTax(), 1.0);
                });
        assertEquals(11.0, receipt.getTotal());
        assertEquals(1.0, receipt.getSalesTaxes());
    }

    @Test
    public void testGenerateReceiptWithFoodType() {
        var products = createProducts(1);
        when(shoppingBasketRepository.findByBasketId(any())).thenReturn(products);

        var response = shoppingBasketService.addToBasket(products);

        var receipt = shoppingBasketService.generateReceipt(response.getBasketId());
        assertEquals(1, receipt.getOrderItems().size());
        receipt.getOrderItems()
                .forEach(item -> {
                    assertEquals(item.getName(), "test 1");
                    assertEquals(item.getQuantity(), 1);
                    assertEquals(item.getPrice(), 10.0);
                    assertEquals(item.getTax(), 0.0);
                });
        assertEquals(10.0, receipt.getTotal());
        assertEquals(0.0, receipt.getSalesTaxes());
    }
}
