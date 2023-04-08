package com.liferay.liferaysalestaxreceipt.config;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Properties;

public class Config {
    private static final String CONFIG_FILE = "config.properties";
    private final BigDecimal basicTaxRate;
    private final BigDecimal importTaxRate;
    private final BigDecimal roundingFactor;

    public Config() {
        Properties properties = new Properties();
        //An input stream that reads the content of the config.properties file
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (inputStream == null) {
                throw new IllegalStateException("Configuration file not found: " + CONFIG_FILE);
            }
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file: " + CONFIG_FILE, e);
        }

        basicTaxRate = new BigDecimal(properties.getProperty("basicTaxRate"));
        importTaxRate = new BigDecimal(properties.getProperty("importTaxRate"));
        roundingFactor = new BigDecimal(properties.getProperty("roundingFactor"));
    }

    public BigDecimal getBasicTaxRate() {
        return basicTaxRate;
    }

    public BigDecimal getImportTaxRate() {
        return importTaxRate;
    }

    public BigDecimal getRoundingFactor() {
        return roundingFactor;
    }
}
