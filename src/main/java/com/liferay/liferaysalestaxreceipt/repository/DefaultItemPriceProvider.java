package com.liferay.liferaysalestaxreceipt.repository;

import java.math.BigDecimal;

public class DefaultItemPriceProvider implements ItemPriceProvider {
    @Override
    public BigDecimal getItemPrice(String itemName) {
        //TODO : implement method to look up item price in a database or service
        // For this example, just return a default price of $1.00
        return new BigDecimal("1.00");
    }
}
