package com.github.peacetrue.util;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.function.Function;

/**
 * 文件工具类
 *
 * @author xiayx
 */
public class FileUtils {

    protected FileUtils() {
    }

    /**
     * 创建文件，当其不存在时
     *
     * @param path 文件路径
     * @throws IOException 创建文件过程中发生异常
     */
    public static void createFileIfAbsent(Path path) throws IOException {
        if (Files.exists(path)) return;

        Path parent = path.getParent();
        if (!Files.exists(parent)) Files.createDirectories(parent);
        Files.createFile(path);
    }

    /**
     * 创建目录，当其不存在时
     *
     * @param path 目录路径
     * @throws IOException 创建目录过程中发生异常
     */
    public static void createFolderIfAbsent(Path path) throws IOException {
        if (Files.exists(path)) return;
        Files.createDirectories(path);
    }

    /**
     * 重命名
     *
     * @param path 路径
     * @throws IOException
     */
    public static void rename(Path path, Function<Path, Path> converter) throws IOException {
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                this.move(dir);
                return super.preVisitDirectory(dir, attrs);
            }

            private void move(Path dir) throws IOException {
                Path convertedPath = converter.apply(dir);
                if (convertedPath == dir) return;
                Files.move(dir, convertedPath, StandardCopyOption.REPLACE_EXISTING);
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                this.move(file);
                return super.visitFile(file, attrs);
            }
        });
    }
}
