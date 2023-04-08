package com.phoenixtype.liferaysalestaxreceipt;

import com.liferay.liferaysalestaxreceipt.model.Item;
import com.liferay.liferaysalestaxreceipt.model.Receipt;
import com.liferay.liferaysalestaxreceipt.model.ReceiptItem;
import com.liferay.liferaysalestaxreceipt.service.ReceiptGenerator;
import com.liferay.liferaysalestaxreceipt.service.TaxCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReceiptGeneratorTest {
    private ReceiptGenerator receiptGenerator;
    private TaxCalculator taxCalculator;

    @BeforeEach
    public void setUp() {
        taxCalculator = mock(TaxCalculator.class);
        when(taxCalculator.calculateTax("book", new BigDecimal("12.49"))).thenReturn(BigDecimal.ZERO);
        when(taxCalculator.calculateTax("music CD", new BigDecimal("14.99"))).thenReturn(new BigDecimal("1.50"));
        when(taxCalculator.calculateTax("chocolate bar", new BigDecimal("0.85"))).thenReturn(BigDecimal.ZERO);

        receiptGenerator = new ReceiptGenerator(taxCalculator);
    }

    @Test
    public void testGenerateReceipt() {
        List<Item> items = Arrays.asList(
                new Item("book", new BigDecimal("12.49"), 1),
                new Item("music CD", new BigDecimal("14.99"), 1),
                new Item("chocolate bar", new BigDecimal("0.85"), 1)
        );

        Receipt receipt = receiptGenerator.generateReceipt(items);

        assertEquals(new BigDecimal("1.50"), receipt.getSalesTaxes());
        assertEquals(new BigDecimal("29.83"), receipt.getTotalCost());

        List<ReceiptItem> receiptItems = receipt.getReceiptItems();
        assertEquals(3, receiptItems.size());

        assertEquals("book", receiptItems.get(0).getItem().getName());
        assertEquals(new BigDecimal("12.49"), receiptItems.get(0).getTotalPrice());
        assertEquals(1, receiptItems.get(0).getItem().getQuantity());

        assertEquals("music CD", receiptItems.get(1).getItem().getName());
        assertEquals(new BigDecimal("16.49"), receiptItems.get(1).getTotalPrice());
        assertEquals(1, receiptItems.get(1).getItem().getQuantity());

        assertEquals("chocolate bar", receiptItems.get(2).getItem().getName());
        assertEquals(new BigDecimal("0.85"), receiptItems.get(2).getTotalPrice());
        assertEquals(1, receiptItems.get(2).getItem().getQuantity());
    }
}


