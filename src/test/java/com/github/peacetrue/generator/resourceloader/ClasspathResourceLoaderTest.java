package com.github.peacetrue.generator.resourceloader;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.List;

/**
 * @author xiayx
 */
public class ClasspathResourceLoaderTest {

    @Test
    public void getResource() throws IOException {
        String location = getClass().getName().replaceAll("\\.", "/") + ".class";
        Resource resource = ClasspathResourceLoader.DEFAULT.getResource(location);
        System.out.println(resource);
        Assert.assertEquals(resource.getPath(), location);
        Assert.assertNotNull(resource.getInputStream());
    }

    @Test
    public void getResourcesFromClasspath() throws IOException {
        String location = getClass().getPackage().getName().replaceAll("\\.", "/");
        List<Resource> resources = ClasspathResourceLoader.DEFAULT.getResources(location);
        System.out.println(resources);
    }

    @Test
    public void getResourcesFromJar() throws IOException {
        String location = Logger.class.getPackage().getName().replaceAll("\\.", "/");
        List<Resource> resources = ClasspathResourceLoader.DEFAULT.getResources(location);
        System.out.println(resources);
        for (Resource simpleFile : resources) {
            if (!simpleFile.isDirectory()) Assert.assertNotNull(simpleFile.getInputStream());
        }
    }

}