package com.example.shopping.basket.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AmountUtil {
    private AmountUtil() {

    }

    public static double roundNumberToDecimalPlaces(double number, int decimals) {
        BigDecimal b = new BigDecimal(String.valueOf(number));
        return b.setScale(decimals, RoundingMode.DOWN).doubleValue();
    }

    public static double roundOffAmount(double amount) {
        return Math.ceil(amount * 20) / 20.0;
    }
}
