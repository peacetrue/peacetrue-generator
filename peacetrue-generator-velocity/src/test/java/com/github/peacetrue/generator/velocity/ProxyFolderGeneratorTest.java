package com.github.peacetrue.generator.velocity;

import com.github.peacetrue.generator.FolderNameGenerator;
import com.github.peacetrue.generator.ProxyFolderGenerator;
import com.github.peacetrue.generator.resourceloader.FileSystemResourceLoader;
import com.github.peacetrue.test.TestUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;

/**
 * @author xiayx
 */
public class ProxyFolderGeneratorTest {

    @Test
    public void generate() throws IOException {
        HashMap<String, Object> context = getContext();
        ProxyFolderGenerator folderGenerator = new ProxyFolderGenerator();
        folderGenerator.setFolderContentGenerator(VelocityFolderContentGeneratorTest.getContentGenerator());
        folderGenerator.setFolderNameGenerator(new FolderNameGenerator(FileSystemResourceLoader.DEFAULT));
        folderGenerator.setSourcePath(getClass().getName().replaceAll("\\.", "/"));
        folderGenerator.setTargetPath(TestUtils.getSourceAbsolutePath(getClass()) + "Target");
        folderGenerator.generate(context);
        int[] count = {0};
        Files.walkFileTree(Paths.get(folderGenerator.getFolderNameGenerator().getTargetPath()), new SimpleFileVisitor<Path>() {
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Assert.assertEquals("生成的内容是正确的", context.get("name"), Files.readAllLines(file).get(0));
                Assert.assertFalse(file.toString().contains("{packageName}"));
                Files.deleteIfExists(file);
                count[0]++;
                return super.visitFile(file, attrs);
            }

            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Assert.assertFalse(dir.toString().contains("{packageName}"));
                Files.deleteIfExists(dir);
                count[0]++;
                return super.postVisitDirectory(dir, exc);
            }
        });
        Assert.assertEquals(7, count[0]);
    }

    public static HashMap<String, Object> getContext() {
        HashMap<String, Object> context = new HashMap<>();
        context.put("packageName", "com.peacetrue");
        context.put("name", "晓玉");
        return context;
    }
}