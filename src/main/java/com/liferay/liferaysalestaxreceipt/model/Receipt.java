package com.liferay.liferaysalestaxreceipt.model;

import java.math.BigDecimal;
import java.util.List;

public class Receipt {
    private final List<ReceiptItem> receiptItems;
    private final BigDecimal salesTaxes;
    private final BigDecimal totalCost;

    public Receipt(List<ReceiptItem> receiptItems, BigDecimal salesTaxes, BigDecimal totalCost) {
        this.receiptItems = receiptItems;
        this.salesTaxes = salesTaxes;
        this.totalCost = totalCost;
    }

    public List<ReceiptItem> getReceiptItems() {
        return receiptItems;
    }

    public BigDecimal getSalesTaxes() {
        return salesTaxes;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }
}
