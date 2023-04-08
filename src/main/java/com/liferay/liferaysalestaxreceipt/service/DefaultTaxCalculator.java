package com.liferay.liferaysalestaxreceipt.service;

import com.liferay.liferaysalestaxreceipt.config.Config;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;

public class DefaultTaxCalculator implements TaxCalculator {
    private final Config config;

    private final Set<String> exemptItems = Set.of("book", "chocolate bar", "chocolates", "pills");

    public DefaultTaxCalculator(Config config) {
        this.config = config;
    }

    @Override
    public BigDecimal calculateTax(String itemName, BigDecimal price) {
        BigDecimal basicTax = BigDecimal.ZERO;
        BigDecimal importTax = BigDecimal.ZERO;

        if (!isTaxExempt(itemName)) {
            basicTax = price.multiply(config.getBasicTaxRate());
        }

        if (isImported(itemName)) {
            importTax = price.multiply(config.getImportTaxRate());
        }

        BigDecimal totalTax = basicTax.add(importTax);
        return roundUp(totalTax);
    }

    private BigDecimal roundUp(BigDecimal salesTax) {
        BigDecimal divided = salesTax.divide(config.getRoundingFactor(), 0, RoundingMode.UP);
        return divided.multiply(config.getRoundingFactor());
    }

    private boolean isImported(String itemName) {
        return itemName.toLowerCase().contains("imported");
    }

    private boolean isTaxExempt(String itemName) {
        return exemptItems.stream().anyMatch(itemName.toLowerCase()::contains);
    }
}