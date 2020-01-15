package com.github.peacetrue.generator;

import com.github.peacetrue.generator.placeholder.PlaceholderResolver;
import com.github.peacetrue.generator.resourceloader.ResourceLoader;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorAutoConfiguration.class)
public class GeneratorAutoConfigurationTest {

    @Autowired
    @Qualifier(GeneratorAutoConfiguration.RESOURCE_LOADER_FILE_SYSTEM)
    private ResourceLoader fileSystemResourceLoader;
    @Autowired
    @Qualifier(GeneratorAutoConfiguration.RESOURCE_LOADER_CLASSPATH)
    private ResourceLoader classpathResourceLoader;
    @Autowired
    private PlaceholderResolver placeholderResolver;
    @Autowired
    @Qualifier(GeneratorAutoConfiguration.GENERATOR_FOLDER_NAME)
    private Generator folderNameGenerator;
    @Autowired(required = false)
    @Qualifier(GeneratorAutoConfiguration.GENERATOR_FOLDER)
    private Generator folderGenerator;

    @Test
    public void fileSystemResourceLoader() {
        Assert.assertNotNull(fileSystemResourceLoader);
    }

    @Test
    public void classpathResourceLoader() {
        Assert.assertNotNull(classpathResourceLoader);
    }

    @Test
    public void placeholderResolver() {
        Assert.assertNotNull(placeholderResolver);
    }

    @Test
    public void folderNameGenerator() {
        Assert.assertNotNull(folderNameGenerator);
    }

    @Test
    public void folderGenerator() {
        Assert.assertNull(folderGenerator);
    }
}