package com.github.selfmadeboy.agent;

import org.tinylog.configuration.ConfigurationLoader;

import java.io.InputStream;
import java.util.Properties;

public class LogConfigurationLoader implements ConfigurationLoader {
    /**
     * Load a property object from a resource.
     *
     * @return The loaded properties
     * @throws Exception Any exception can be thrown if writing has been failed
     */
    @Override
    public Properties load() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("writer1","console");
        properties.setProperty("writer1.format","{date: yyyy-MM-dd HH:mm:ss.SSS} {level}: --- [{thread}] {class}#{method}() [{line}] {message}");
        properties.setProperty("writer2","rolling file");
        properties.setProperty("writer2.file","#{agent.log.home:/var/log}/multi-agent-{count}.log");
        properties.setProperty("writer2.latest","#{agent.log.home:/var/log}/multi-agent-latest.log");
        properties.setProperty("writer2.policies","size: 10mb");
        properties.setProperty("writer2.format","{date: yyyy-MM-dd HH:mm:ss.SSS} {level}: --- [{thread}] {class}#{method}() [{line}] {message}");
        properties.setProperty("writer2.backlups","10");
        return properties;
    }
}
