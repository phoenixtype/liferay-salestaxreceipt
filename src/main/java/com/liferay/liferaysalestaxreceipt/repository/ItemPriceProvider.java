package com.liferay.liferaysalestaxreceipt.repository;

import java.math.BigDecimal;

public interface ItemPriceProvider {
    BigDecimal getItemPrice(String itemName);
}
