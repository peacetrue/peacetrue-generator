package com.github.peacetrue.generator;

import com.github.peacetrue.generator.resourceloader.ClasspathResourceLoader;
import com.github.peacetrue.test.TestUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * @author xiayx
 */
public class FolderNameGeneratorTest {

    @Test
    public void generate() throws IOException {
        FolderNameGenerator folderNameGenerator = new FolderNameGenerator();
        folderNameGenerator.setResourceLoader(ClasspathResourceLoader.DEFAULT);
        folderNameGenerator.setSourcePath(getClass().getName().replaceAll("\\.", "/"));
        folderNameGenerator.setTargetPath(TestUtils.getAbsoluteFolder(getClass()) + "/" + getClass().getSimpleName() + "Target");
        HashMap<String, Object> context = getContext();
        folderNameGenerator.generate(context);
        Assert.assertTrue(Files.exists(Paths.get(folderNameGenerator.getTargetPath(),"src/main/java/com/peacetrue/Demo.java.vm")));
        Assert.assertTrue(Files.exists(Paths.get(folderNameGenerator.getTargetPath(),"src/main/java/com/peacetrue/DemoMapper.java.vm")));
        FileSystemUtils.deleteRecursively(new File(folderNameGenerator.getTargetPath()));
    }

    private HashMap<String, Object> getContext() {
        HashMap<String, Object> context = new HashMap<>();
        context.put("packageName", "com.peacetrue");
        context.put("Name", "Demo");
        return context;
    }
}