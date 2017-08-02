package com.jmeplay.core.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

/**
 * Store and load settings for whole application
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class Settings {
    private String settingsFile = "settings.xml";
    private Properties settings;

    @PostConstruct
    private void init() {
        settings = new Properties();
        loadSettings();
    }

    @PreDestroy
    private void destroy() {
        writeSettings();
    }

    private void loadSettings() {
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(settingsFile);
            settings.loadFromXML(inputStream);
            inputStream.close();
        } catch (Exception e) {
            throw new IllegalArgumentException("Can't read settings file", e);
        }
    }

    private void writeSettings() {
        OutputStream outputStream;
        try {
            outputStream = new FileOutputStream(settingsFile);
            settings.storeToXML(outputStream, "Settings for whole application");
        } catch (Exception e) {
            throw new IllegalArgumentException("Can't write settings file", e);
        }
    }

    public void setOption(String key, String value) {
        settings.setProperty(key, value);
    }

    public void removeOption(String key) {
        settings.remove(key);
    }

    public Boolean getOptionAsBoolean(String key, Boolean defaultValue) {
        String option = settings.getProperty(key);
        if (option == null) {
            setOption(key, "" + defaultValue);
            return defaultValue;
        }
        return Boolean.parseBoolean(option);
    }

    public String getOptionAsString(String key, String defaultValue) {
        String option = settings.getProperty(key);
        if (option == null) {
            setOption(key, defaultValue);
            return defaultValue;
        }
        return option;
    }

    public Integer getOptionInteger(String key, Integer defaultValue) {
        String option = settings.getProperty(key);
        if (option == null) {
            setOption(key, "" + defaultValue);
            return defaultValue;
        }
        return Integer.parseInt(option);
    }

    public Double getOptionDouble(String key, Double defaultValue) {
        String option = settings.getProperty(key);
        if (option == null) {
            setOption(key, "" + defaultValue);
            return defaultValue;
        }
        return Double.parseDouble(option);
    }
}
