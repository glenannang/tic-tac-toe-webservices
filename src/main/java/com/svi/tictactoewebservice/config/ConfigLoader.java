package com.svi.tictactoewebservice.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Loads application configuration from {@code config.properties} on the classpath.
 *
 * <p>Implemented as a lazily-initialized, thread-safe singleton (initialization-on-demand
 * holder idiom) so the file is read exactly once per JVM.</p>
 */
public final class ConfigLoader {

    private static final String CONFIG_FILE_NAME = "config.properties";

    private final Properties properties;

    private ConfigLoader() {
        this.properties = loadProperties();
    }

    private static class Holder {
        private static final ConfigLoader INSTANCE = new ConfigLoader();
    }

    public static ConfigLoader getInstance() {
        return Holder.INSTANCE;
    }

    private Properties loadProperties() {
        Properties props = new Properties();
        try (InputStream input = ConfigLoader.class.getClassLoader()
                .getResourceAsStream(CONFIG_FILE_NAME)) {

            if (input == null) {
                throw new IllegalStateException(
                        "Unable to find " + CONFIG_FILE_NAME + " on the classpath");
            }
            props.load(input);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load " + CONFIG_FILE_NAME, e);
        }
        return props;
    }

    public String getCassandraIp() {
        return getRequired("CASSANDRA_IP");
    }

    public int getCassandraPort() {
        return Integer.parseInt(getRequired("CASSANDRA_PORT"));
    }

    public String getCassandraKeyspace() {
        return getRequired("CASSANDRA_KEYSPACE");
    }

    public String getCassandraTable() {
        return getRequired("CASSANDRA_TABLE");
    }

    private String getRequired(String key) {
        String value = properties.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalStateException("Missing required config property: " + key);
        }
        return value.trim();
    }
}