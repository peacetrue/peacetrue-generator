package com.github.peacetrue.generator.velocity;

import com.github.peacetrue.generator.resourceloader.ClasspathResourceLoader;
import com.github.peacetrue.test.TestUtils;
import org.apache.velocity.app.VelocityEngine;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiayx
 */
public class VelocityFolderContentGeneratorTest {

    @Test
    public void generate() throws IOException {
        Map<String, Object> context = getContext();
        VelocityFolderContentGenerator velocityFolderGenerator = getContentGenerator();
        velocityFolderGenerator.setSourcePath(VelocityFolderContentGeneratorTest.class.getName().replaceAll("\\.", "/"));
        velocityFolderGenerator.setTargetPath(TestUtils.getAbsoluteFolder(VelocityFolderContentGeneratorTest.class) + "/" + VelocityFolderContentGeneratorTest.class.getSimpleName() + "Target");
        velocityFolderGenerator.generate(getContext());

        Assert.assertFalse("被忽略的文件不应该生成", Files.exists(Paths.get(velocityFolderGenerator.getTargetPath() + "/ignored.vm")));
        Path targetPath = Paths.get(velocityFolderGenerator.getTargetPath());
        int[] count = {0};
        Files.walkFileTree(targetPath, new SimpleFileVisitor<Path>() {
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Assert.assertEquals("生成的内容是正确的", context.get("name"), Files.readAllLines(file).get(0));
                Files.deleteIfExists(file);
                count[0]++;
                return super.visitFile(file, attrs);
            }

            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.deleteIfExists(dir);
                count[0]++;
                return super.postVisitDirectory(dir, exc);
            }
        });
        Assert.assertEquals(6, count[0]);
    }

    private Map<String, Object> getContext() {
        Map<String, Object> context = new HashMap<>();
        context.put("name", "晓玉");
        return context;
    }

    public static VelocityFolderContentGenerator getContentGenerator() {
        VelocityFolderContentGenerator velocityFolderGenerator = new VelocityFolderContentGenerator();

        velocityFolderGenerator.setResourceLoader(ClasspathResourceLoader.DEFAULT);
        velocityFolderGenerator.setIgnoredPredicate(path -> path.endsWith("ignored.vm"));
        velocityFolderGenerator.setParsedPredicate(path -> path.endsWith(".vm"));
        velocityFolderGenerator.setTargetPathHandler(path -> path.endsWith(".vm") ? path.substring(0, path.length() - 3) + ".txt" : path);
        velocityFolderGenerator.setVelocityEngine(new VelocityEngine());
        return velocityFolderGenerator;
    }

}