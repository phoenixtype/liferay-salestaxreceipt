package com.phoenixtype.liferaysalestaxreceipt;

import com.liferay.liferaysalestaxreceipt.config.Config;
import com.liferay.liferaysalestaxreceipt.service.DefaultTaxCalculator;
import com.liferay.liferaysalestaxreceipt.service.TaxCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultTaxCalculatorTest {
    private Config config;
    private TaxCalculator taxCalculator;

    @BeforeEach
    void setUp() {
        config = new Config();
        taxCalculator = new DefaultTaxCalculator(config);
    }

    @Test
    void testExemptItem() {
        BigDecimal price = new BigDecimal("12.49");
        BigDecimal expectedTax = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        BigDecimal actualTax = taxCalculator.calculateTax("book", price, item.getQuantity());
        assertEquals(expectedTax, actualTax);
    }

    @Test
    void testNonExemptItem() {
        BigDecimal price = new BigDecimal("14.99");
        BigDecimal expectedTax = new BigDecimal("1.50");
        BigDecimal actualTax = taxCalculator.calculateTax("music CD", price, item.getQuantity());
        assertEquals(expectedTax, actualTax);
    }

    @Test
    void testImportedExemptItem() {
        BigDecimal price = new BigDecimal("10.00");
        BigDecimal expectedTax = new BigDecimal("0.50");
        BigDecimal actualTax = taxCalculator.calculateTax("imported box of chocolates", price, item.getQuantity());
        assertEquals(expectedTax, actualTax);
    }

    @Test
    void testImportedNonExemptItem() {
        BigDecimal price = new BigDecimal("47.50");
        BigDecimal expectedTax = new BigDecimal("7.15");
        BigDecimal actualTax = taxCalculator.calculateTax("imported bottle of perfume", price, item.getQuantity());
        assertEquals(expectedTax, actualTax);
    }

    @Test
    void testZeroPrice() {
        BigDecimal price = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        BigDecimal expectedTax = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        BigDecimal actualTax = taxCalculator.calculateTax("bottle of perfume", price, item.getQuantity());
        assertEquals(expectedTax, actualTax);
    }

    @Test
    void testEdgeCaseHighPrice() {
        BigDecimal price = new BigDecimal("10000.00");
        BigDecimal expectedTax = new BigDecimal("1000.00");
        BigDecimal actualTax = taxCalculator.calculateTax("expensive non-exempt item", price, item.getQuantity());
        assertEquals(expectedTax, actualTax);
    }
}
