package com.example.shopping.basket.model;

import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Receipt {

    private Instant timestamp;

    private List<OrderItem> orderItems;

    private double salesTaxes;

    private double total;
}
