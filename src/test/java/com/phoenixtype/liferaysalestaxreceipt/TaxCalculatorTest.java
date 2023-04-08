package com.phoenixtype.liferaysalestaxreceipt;


import com.liferay.liferaysalestaxreceipt.config.Config;
import com.liferay.liferaysalestaxreceipt.service.DefaultTaxCalculator;
import com.liferay.liferaysalestaxreceipt.service.TaxCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TaxCalculatorTest {
    private TaxCalculator taxCalculator;
    private Config config;

    @BeforeEach
    public void setUp() {
        config = mock(Config.class);
        when(config.getBasicTaxRate()).thenReturn(new BigDecimal("0.10"));
        when(config.getImportTaxRate()).thenReturn(new BigDecimal("0.05"));
        when(config.getRoundingFactor()).thenReturn(new BigDecimal("0.05"));

        taxCalculator = new DefaultTaxCalculator(config);
    }

    @Test
    public void testCalculateTaxForNonExemptNonImportedItem() {
        BigDecimal tax = taxCalculator.calculateTax("non-exempt item", new BigDecimal("10.00"), item.getQuantity());
        assertEquals(new BigDecimal("1.00"), tax);
    }

    @Test
    public void testCalculateTaxForExemptNonImportedItem() {
        BigDecimal tax = taxCalculator.calculateTax("book", new BigDecimal("10.00"), item.getQuantity());
        assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), tax);
    }

    @Test
    public void testCalculateTaxForNonExemptImportedItem() {
        BigDecimal tax = taxCalculator.calculateTax("imported non-exempt item", new BigDecimal("10.00"), item.getQuantity());
        assertEquals(new BigDecimal("1.50"), tax);
    }

    @Test
    public void testCalculateTaxForExemptImportedItem() {
        BigDecimal tax = taxCalculator.calculateTax("imported book", new BigDecimal("10.00"), item.getQuantity());
        assertEquals(new BigDecimal("0.50"), tax);
    }
}
