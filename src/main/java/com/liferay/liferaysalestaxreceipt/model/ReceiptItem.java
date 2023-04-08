package com.liferay.liferaysalestaxreceipt.model;

import java.math.BigDecimal;

public class ReceiptItem {
    private final Item item;
    private final BigDecimal totalPrice;

    public ReceiptItem(Item item, BigDecimal totalPrice) {
        this.item = item;
        this.totalPrice = totalPrice;
    }

    public Item getItem() {
        return item;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
}
