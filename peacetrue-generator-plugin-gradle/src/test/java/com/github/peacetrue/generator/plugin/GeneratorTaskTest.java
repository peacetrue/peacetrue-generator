package com.github.peacetrue.generator.plugin;

import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

public class GeneratorTaskTest {

    @Test
    public void generate() throws IOException {
        // Create a test project and apply the plugin
        Project project = ProjectBuilder.builder().build();
        project.getPlugins().apply(GeneratorPlugin.class);

        // Verify the result
        GeneratorTask generateStructure = (GeneratorTask) project.getTasks().findByName("generateStructure");
        assertNotNull(generateStructure);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("peacetrue.generator.target-path", "/Users/xiayx/Documents/Projects/peacetrue-generator/peacetrue-dictionary");
        generateStructure.setProperties(properties);
        generateStructure.setContext(getContext());
        generateStructure.generate();
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