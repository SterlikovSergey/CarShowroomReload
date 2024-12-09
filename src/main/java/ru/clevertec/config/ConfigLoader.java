package ru.clevertec.config;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class ConfigLoader {

    private static final Map<String, Object> applicationConfig;
    private static final Map<String, String> secretConfig;

    static {
        try (InputStream inputStream = ConfigLoader.class.getClassLoader().getResourceAsStream("application.yml")) {
            applicationConfig = new Yaml().load(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load application.yml", e);
        }

        try (InputStream inputStream = ConfigLoader.class.getClassLoader().getResourceAsStream("secret-settings.yml")) {
            secretConfig = new Yaml().load(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load secret-settings.yml", e);
        }
    }

    public static String get(String key) {
        if (secretConfig.containsKey(key)) {
            return secretConfig.get(key);
        }
        String[] keys = key.split("\\.");
        Map<String, Object> current = applicationConfig;

        for (int i = 0; i < keys.length - 1; i++) {
            current = (Map<String, Object>) current.get(keys[i]);
        }
        return current.get(keys[keys.length - 1]).toString();
    }
}
