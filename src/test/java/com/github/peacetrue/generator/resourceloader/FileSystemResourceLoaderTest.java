package com.github.peacetrue.generator.resourceloader;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author xiayx
 */
public class FileSystemResourceLoaderTest {

    @Test
    public void getResource() throws IOException {
        String location = getClass().getResource(getClass().getSimpleName() + ".class").getPath();
        Resource simpleFile = FileSystemResourceLoader.DEFAULT.getResource(location);
        System.out.println(simpleFile);
        Assert.assertEquals(simpleFile.getPath(), location);
        Assert.assertNotNull(simpleFile.getInputStream());
    }

    @Test
    public void getResources() throws IOException {
        String location = getClass().getResource(getClass().getSimpleName() + ".class").getPath();
        location = Paths.get(location).resolveSibling("").toString();
        List<Resource> files = FileSystemResourceLoader.DEFAULT.getResources(location);
        Assert.assertEquals(3, files.size());
        for (Resource file : files) {
            if (!file.isDirectory()) Assert.assertNotNull(file.getInputStream());
        }
    }
}