package com.github.peacetrue.generator.velocity;

import com.github.peacetrue.generator.resourceloader.ClasspathResourceLoader;
import com.github.peacetrue.test.TestUtils;
import org.apache.velocity.app.VelocityEngine;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiayx
 */
public class VelocityGeneratorTest {

    @Test
    public void generate() throws IOException {
        VelocityFileGenerator velocityGenerator = new VelocityFileGenerator();
        velocityGenerator.setSourcePath(getClass().getName().replaceAll("\\.", "/") + ".vm");
        velocityGenerator.setResourceLoader(ClasspathResourceLoader.DEFAULT);
        velocityGenerator.setTargetPath(TestUtils.getAbsoluteFolder(getClass()) + "/" + getClass().getSimpleName() + ".txt");
        velocityGenerator.setVelocityEngine(new VelocityEngine());
        Map<String, Object> context = new HashMap<>();
        context.put("name", "晓玉");
        velocityGenerator.generate(context);

        Path targetPath = Paths.get(velocityGenerator.getTargetPath());
        Assert.assertEquals(context.get("name"), Files.readAllLines(targetPath).get(0));
        Files.deleteIfExists(targetPath);
    }


}