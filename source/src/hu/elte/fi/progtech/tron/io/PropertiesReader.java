package hu.elte.fi.progtech.tron.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class PropertiesReader {
    private PropertiesReader() {
    }

    public static Properties readProperties(String path) {
        try (final FileInputStream fileInputStream = new FileInputStream(new File(path))) {
            final Properties properties = new Properties();
            properties.load(fileInputStream);
            return properties;
        } catch (IOException ex) {
            throw new IllegalArgumentException("Failed to read the properties file from the given path!", ex);
        }
    }
}
