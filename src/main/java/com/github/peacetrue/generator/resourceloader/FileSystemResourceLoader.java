package com.github.peacetrue.generator.resourceloader;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;

/**
 * 本地文件加载器，从文件系统加载文件
 *
 * @author xiayx
 */
public class FileSystemResourceLoader implements ResourceLoader {

    public static final FileSystemResourceLoader DEFAULT = new FileSystemResourceLoader();

    private FileSystemResourceLoader() {
    }

    @Override
    public List<Resource> getResources(String location) throws IOException {
        List<Resource> resources = new LinkedList<>();
        Files.walkFileTree(Paths.get(location), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                resources.add(new ResourceImpl(dir.toString(), true));
                return super.preVisitDirectory(dir, attrs);
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                resources.add(new FileSystemResource(file.toString(), false));
                return super.visitFile(file, attrs);
            }
        });
        return resources;
    }
}
