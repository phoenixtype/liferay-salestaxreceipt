package com.liferay.liferaysalestaxreceipt.service;

import com.liferay.liferaysalestaxreceipt.model.Item;
import com.liferay.liferaysalestaxreceipt.model.Receipt;
import com.liferay.liferaysalestaxreceipt.model.ReceiptItem;
import com.liferay.liferaysalestaxreceipt.repository.ItemPriceProvider;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SalesTaxApp {
    private final TaxCalculator taxCalculator;
    private final ItemPriceProvider itemPriceProvider;
    private static final Pattern INPUT_PATTERN = Pattern.compile("(\\d+)\\s+([^\\d]+)\\s+at\\s+(\\d+\\.\\d{2})");

    public SalesTaxApp(TaxCalculator taxCalculator, ItemPriceProvider itemPriceProvider) {
        this.taxCalculator = taxCalculator;
        this.itemPriceProvider = itemPriceProvider;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        List<Item> items = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.trim().isEmpty()) {
                break;
            }

            Matcher matcher = INPUT_PATTERN.matcher(line);
            if (!matcher.matches()) {
                throw new IllegalArgumentException("Invalid input format: " + line);
            }

            int quantity = Integer.parseInt(matcher.group(1));
            String itemName = matcher.group(2);
            BigDecimal price = new BigDecimal(matcher.group(3));

            items.add(new Item(itemName, price, quantity));
        }

        ReceiptGenerator receiptGenerator = new ReceiptGenerator(taxCalculator);
        Receipt receipt = receiptGenerator.generateReceipt(items);

        for (ReceiptItem receiptItem : receipt.getReceiptItems()) {
            Item item = receiptItem.getItem();
            System.out.println(item.getQuantity() + " " + item.getName() + ": " + String.format("%.2f", receiptItem.getTotalPrice()));
        }

        System.out.println("Sales Taxes: " + String.format("%.2f", receipt.getSalesTaxes()));
        System.out.println("Total: " + String.format("%.2f", receipt.getTotalCost()));
    }
}
