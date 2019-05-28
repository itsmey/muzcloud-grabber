package ru.imikryakov.projects.mp3downloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

class Config {
    private static Logger logger = LoggerFactory.getLogger(Config.class);

    private static URL configFile = Config.class.getClassLoader().getResource("config.properties");

    static String getConfigProperty(String propName) {
        if (configFile == null) {
            logger.error("config file not found for property " + propName);
            throw new IllegalStateException("config file not found for property " + propName);
        } else {
            try (InputStream input = new FileInputStream(configFile.getFile())) {
                Properties p = new Properties();
                p.load(input);
                return p.getProperty(propName);
            } catch (Exception e) {
                logger.error("properties config file error for property " + propName, e);
                throw new IllegalStateException("properties config file error for property " + propName);
            }
        }
    }
}
