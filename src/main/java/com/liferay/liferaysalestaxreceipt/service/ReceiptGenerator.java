package com.liferay.liferaysalestaxreceipt.service;

import com.liferay.liferaysalestaxreceipt.model.Item;
import com.liferay.liferaysalestaxreceipt.model.Receipt;
import com.liferay.liferaysalestaxreceipt.model.ReceiptItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ReceiptGenerator {
    private final TaxCalculator taxCalculator;

    public ReceiptGenerator(TaxCalculator taxCalculator) {
        this.taxCalculator = taxCalculator;
    }

    public Receipt generateReceipt(List<Item> items) {
        BigDecimal totalSalesTax = BigDecimal.ZERO;
        BigDecimal totalCost = BigDecimal.ZERO;
        List<ReceiptItem> receiptItems = new ArrayList<>();

        for (Item item : items) {
            BigDecimal salesTax = taxCalculator.calculateTax(item.getName(), item.getPrice());
            BigDecimal totalPrice = item.getPrice().add(salesTax).multiply(new BigDecimal(item.getQuantity()));

            totalSalesTax = totalSalesTax.add(salesTax);
            totalCost = totalCost.add(totalPrice);

            receiptItems.add(new ReceiptItem(item, totalPrice));
        }

        return new Receipt(receiptItems, totalSalesTax, totalCost);
    }
}
