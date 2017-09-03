package net.melaircraft.twitchtohue.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuration {
    private final String hueAddress;
    private final String hueAuthToken;
    private final int hueLightNumber;
    private final int hueOnTime;
    private final int httpPort;

    public Configuration() throws ConfigurationException {
        final Properties rawProperties = new Properties();

        try (FileInputStream file = new FileInputStream("twitchtohue.properties")) {
            rawProperties.load(file);
        } catch (IOException e) {
            throw new ConfigurationException("Could not load configuration due to a file handling problem.", e);
        }

        hueAddress = getOrThrow(rawProperties, "hue.address");
        hueAuthToken = getOrThrow(rawProperties, "hue.token");

        final String hueLightString = getOrThrow(rawProperties, "hue.lightId");

        try {
            hueLightNumber = Integer.parseInt(hueLightString);
        } catch (NumberFormatException e) {
            throw new ConfigurationException("Could not parse 'hue.lightId' into an integer.");
        }

        final String hueOnTimeString = getOrThrow(rawProperties, "hue.onTime");

        try {
            hueOnTime = Integer.parseInt(hueOnTimeString);
        } catch (NumberFormatException e) {
            throw new ConfigurationException("Could not parse 'hue.onTime' into an integer.");
        }

        final String httpPortString = getOrThrow(rawProperties, "http.port");

        try {
            httpPort = Integer.parseInt(httpPortString);
        } catch (NumberFormatException e) {
            throw new ConfigurationException("Could not parse 'http.port' into an integer.");
        }
    }

    public String getHueAddress() {
        return hueAddress;
    }

    public String getHueAuthToken() {
        return hueAuthToken;
    }

    public int getHueOnTime() {
        return hueOnTime;
    }

    public int getHueLightNumber() {
        return hueLightNumber;
    }

    public int getHttpPort() {
        return httpPort;
    }

    private String getOrThrow(Properties properties, String key) throws ConfigurationException {
        final String value = properties.getProperty(key);

        if (value == null) {
            throw new ConfigurationException("Could not find '" + key + "' in configuration.");
        } else {
            return value;
        }
    }
}
