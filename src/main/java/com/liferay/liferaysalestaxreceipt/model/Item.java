package com.liferay.liferaysalestaxreceipt.model;

import java.math.BigDecimal;

public class Item {
    private final String name;
    private final BigDecimal price;
    private final int quantity;

    public Item(String name, BigDecimal price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
