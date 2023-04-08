package com.liferay.liferaysalestaxreceipt.service;

import java.math.BigDecimal;

public interface TaxCalculator {
    BigDecimal calculateTax(String itemName, BigDecimal price);
}
