package com.liferay.liferaysalestaxreceipt;

import com.liferay.liferaysalestaxreceipt.config.Config;
import com.liferay.liferaysalestaxreceipt.repository.DefaultItemPriceProvider;
import com.liferay.liferaysalestaxreceipt.repository.ItemPriceProvider;
import com.liferay.liferaysalestaxreceipt.service.DefaultTaxCalculator;
import com.liferay.liferaysalestaxreceipt.service.SalesTaxApp;
import com.liferay.liferaysalestaxreceipt.service.TaxCalculator;

/*
 SalesTaxCalculator, is the main class of a Sales Tax Calculation application.
 It contains the main method, which is the entry point of the application when it is executed.
 */

public class SalesTaxCalculator {
    public static void main(String[] args) {
        Config config = new Config();
        TaxCalculator taxCalculator = new DefaultTaxCalculator(config);
        ItemPriceProvider itemPriceProvider = new DefaultItemPriceProvider();
        SalesTaxApp salesTaxApp = new SalesTaxApp(taxCalculator, itemPriceProvider);
        salesTaxApp.run();
    }
}
