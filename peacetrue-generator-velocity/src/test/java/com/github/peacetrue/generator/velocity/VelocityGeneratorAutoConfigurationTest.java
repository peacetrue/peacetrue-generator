package com.github.peacetrue.generator.velocity;

import com.github.peacetrue.generator.FolderGeneratorAutoConfiguration;
import com.github.peacetrue.generator.Generator;
import com.github.peacetrue.generator.GeneratorAutoConfiguration;
import org.apache.velocity.app.VelocityEngine;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        GeneratorAutoConfiguration.class,
//        FolderGeneratorAutoConfiguration.class,
        VelocityGeneratorAutoConfiguration.class,
})
public class VelocityGeneratorAutoConfigurationTest {

    @Autowired
    private VelocityEngine classpathVelocityEngine;
    @Autowired
    private ContextFactory contextFactory;
    @Autowired
    @Qualifier(GeneratorAutoConfiguration.GENERATOR_FILE)
    private Generator fileGenerator;
    @Autowired
    @Qualifier(GeneratorAutoConfiguration.GENERATOR_FOLDER_CONTENT)
    private Generator folderContentGenerator;
    @Autowired
    @Qualifier(GeneratorAutoConfiguration.GENERATOR_FOLDER)
    private Generator folderGenerator;

    @Test
    public void classpathVelocityEngine() {
        Assert.assertNotNull(classpathVelocityEngine);
    }

    @Test
    public void contextFactory() {
        Assert.assertNotNull(contextFactory);
    }

    @Test
    public void folderContentGenerator() {
        Assert.assertNotNull(folderContentGenerator);
    }

    @Test
    public void folderGenerator() throws IOException {
        Assert.assertNotNull(folderGenerator);
    }

    @Test
    public void fileGenerator() throws IOException {
        Assert.assertNotNull(fileGenerator);
    }
}