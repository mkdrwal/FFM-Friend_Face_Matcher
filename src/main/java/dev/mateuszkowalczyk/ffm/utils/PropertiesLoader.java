package dev.mateuszkowalczyk.ffm.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class PropertiesLoader {
    public static final String CONFIG_NAME_FILE = "config.properties";

    private static final PropertiesLoader instance = new PropertiesLoader();
    private ResourceLoader resourceLoader = ResourceLoader.getInstance();
    private Properties properties = null;

    public PropertiesLoader () {}

    public static PropertiesLoader getInstance() {
        return instance;
    }

    public Properties load() {
        this.properties = new Properties();
        try {
            InputStream inputStream = resourceLoader.getResourceAsStream(CONFIG_NAME_FILE);
            this.properties.load(inputStream);

            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.properties;
    }

    public void save(Properties prop) {
        try {
            OutputStream output = new FileOutputStream(this.resourceLoader.getPath(CONFIG_NAME_FILE));
            prop.store(output, null);

            output.close();
            System.out.println(prop);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public String get(String name) {
        if (this.properties == null) {
            this.load();
        }

        return this.properties.getProperty(name);
    }
}
