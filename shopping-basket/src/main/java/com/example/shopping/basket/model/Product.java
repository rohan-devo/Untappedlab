package com.example.shopping.basket.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Product {

    private int quantity;

    private String name;

    @JsonProperty("isImported")
    private boolean isImported;

    private double price;

    private ProductType type;

    private UUID basketId;

    @Id
    @GeneratedValue
    private int id;
}
