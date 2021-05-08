package com.example.shopping.basket.repository;

import com.example.shopping.basket.model.Product;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingBasketJPARepository extends CrudRepository<Product, Integer> {

    List<Product> findByBasketId(UUID basketId);
}
