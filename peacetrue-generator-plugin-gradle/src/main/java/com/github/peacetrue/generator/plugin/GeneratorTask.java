package com.github.peacetrue.generator.plugin;

import lombok.Getter;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.IOException;
import java.util.Map;

@Getter
public class GeneratorTask extends DefaultTask {

    private GeneratorClient client = new GeneratorClient();

    @TaskAction
    public void generate() throws IOException {
        client.generate();
    }

    public void setProperties(Map<String, Object> properties) {
        client.setProperties(properties);
    }

    public void setContext(Map<String, Object> context) {
        this.client.setContext(context);
    }
}