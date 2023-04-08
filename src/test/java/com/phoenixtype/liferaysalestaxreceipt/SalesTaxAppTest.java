package com.phoenixtype.liferaysalestaxreceipt;

import com.liferay.liferaysalestaxreceipt.config.Config;
import com.liferay.liferaysalestaxreceipt.repository.ItemPriceProvider;
import com.liferay.liferaysalestaxreceipt.service.DefaultTaxCalculator;
import com.liferay.liferaysalestaxreceipt.service.SalesTaxApp;
import com.liferay.liferaysalestaxreceipt.service.TaxCalculator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;


class SalesTaxAppTest {

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private ItemPriceProvider itemPriceProvider;
    private SalesTaxApp salesTaxApp;
    private ByteArrayOutputStream outputStream;



    @BeforeEach
    void setUp() {
        Config config = new Config();
        TaxCalculator taxCalculator = new DefaultTaxCalculator(config);
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        itemPriceProvider = mock(ItemPriceProvider.class);
        salesTaxApp = new SalesTaxApp(taxCalculator, itemPriceProvider);
    }

    @AfterEach
    public void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    @Test
    public void testScenario1() {
        String input = "1 book at 12.49\n" +
                "1 music CD at 14.99\n" +
                "1 chocolate bar at 0.85\n";

        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));

        String expectedOutput = "1 book: 12.49\n" +
                "1 music CD: 16.49\n" +
                "1 chocolate bar: 0.85\n" +
                "Sales Taxes: 1.50\n" +
                "Total: 29.83\n";

        salesTaxApp.run();

        String actualOutput = outputStream.toString().replaceAll("\r\n", "\n");
        assertEquals(expectedOutput, actualOutput);
    }
    @Test
    void testScenario2() {
        String input = "1 imported box of chocolates at 10.00\n" +
        "1 imported bottle of perfume at 47.50\n";

        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));

        String expectedOutput = "1 imported box of chocolates: 10.50\n" +
                "1 imported bottle of perfume: 54.65\n" +
                "Sales Taxes: 7.65\n" +
                "Total: 65.15\n";

        salesTaxApp.run();

        String actualOutput = outputStream.toString().replaceAll("\r\n", "\n");
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testScenario3() {
        String input = "1 imported bottle of perfume at 27.99\n" +
                "1 bottle of perfume at 18.99\n" +
                "1 packet of headache pills at 9.75\n" +
                "1 imported box of chocolates at 11.25\n\n";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));

        String expectedOutput = "1 imported bottle of perfume: 32.19\n" +
                "1 bottle of perfume: 20.89\n" +
                "1 packet of headache pills: 9.75\n" +
                "1 imported box of chocolates: 11.85\n" +
                "Sales Taxes: 6.70\n" +
                "Total: 74.68\n";

        salesTaxApp.run();

        String actualOutput = outputStream.toString().replaceAll("\r\n", "\n");
        assertEquals(expectedOutput, actualOutput);
    }
}
