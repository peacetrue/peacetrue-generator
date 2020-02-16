package com.github.peacetrue.generator.plugin;

import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GeneratorClientTest {

    @Test
    public void generate() throws IOException {
        GeneratorClient generatorClient = new GeneratorClient();
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("peacetrue.generator.target-path", "/Users/xiayx/Documents/Projects/peacetrue-generator/peacetrue-generator-plugin/src/test/resources/plugin");
        generatorClient.setProperties(properties);
        generatorClient.setContext(getContext());
        generatorClient.generate();
    }

    public static Map<String, Object> getContext() {
        Map<String, Object> context = new HashMap<>();
        context.put("project-name", "peacetrue-dictionary");
        context.put("basePackageName", "com.github.peacetrue.dictionary");
        context.put("DomainName", "Dictionary");
        context.put("domainDescription", "字典");
        return context;
    }

}