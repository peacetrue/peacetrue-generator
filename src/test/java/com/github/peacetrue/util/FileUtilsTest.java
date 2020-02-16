package com.github.peacetrue.util;

import com.github.peacetrue.test.TestUtils;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author xiayx
 */
public class FileUtilsTest {

    @Test
    public void createFileIfAbsent() throws IOException {
        //TODO 不好测试
    }

    @Test
    public void createFolderIfAbsent() throws IOException {
        //TODO 不好测试
    }

    @Test
    public void rename() throws IOException {
        String sourceFolderAbsolutePath = TestUtils.getSourceFolderAbsolutePath(FileUtilsTest.class) + "/FileUtilsRename";
        Path path1 = Paths.get(sourceFolderAbsolutePath);
        FileUtils.createFileIfAbsent(path1.resolve("a/a.txt"));
        FileUtils.createFileIfAbsent(path1.resolve("a.txt"));
        FileUtils.rename(Paths.get(sourceFolderAbsolutePath), (path, isDir) -> isDir ? path : path.resolveSibling("b.txt"));
    }

}