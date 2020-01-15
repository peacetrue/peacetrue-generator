package com.github.peacetrue.generator.resourceloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * 类路径资源加载器
 *
 * @author xiayx
 */
public class ClasspathResourceLoader implements ResourceLoader {

    public static final ClasspathResourceLoader DEFAULT = new ClasspathResourceLoader();

    private Logger logger = LoggerFactory.getLogger(getClass());

    private ClassLoader classLoader;

    public ClasspathResourceLoader() {
        this(ClassLoader.getSystemClassLoader());
    }

    public ClasspathResourceLoader(ClassLoader classLoader) {
        this.classLoader = Objects.requireNonNull(classLoader);
    }

    @Override
    public Resource getResource(String location) {
        location = format(location);
        return new ClasspathResource(location, false, classLoader);
    }

    /**
     * 查找文件
     *
     * <pre>
     * ClasspathSimpleFileLoader.getResources("com/github/peacetrue/util");
     * 输出：
     * SampleFile(path=com/github/peacetrue/util, isDirectory=true)
     * SampleFile(path=com/github/peacetrue/util/ClasspathUtils.class, isDirectory=false)
     * SampleFile(path=com/github/peacetrue/util/SampleFile.class, isDirectory=false)
     * </pre>
     *
     * @param location 类路径，格式为：a/b/c，自动格式化以/开头的路径
     * @return 类路径下的文件列表
     * @throws IOException 查找文件过程中发生读取异常
     */
    @Override
    public List<Resource> getResources(String location) throws IOException {
        logger.info("查找类路径[{}]下的文件", location);
        location = format(location);
        Enumeration<URL> resources = ClassLoader.getSystemResources(location);
        List<Resource> files = new LinkedList<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            String resourceAbsolutePath = resource.getFile();
            //file:/Users/xiayx/.gradle/caches/modules-2/files-2.1/ch.qos.logback/logback-classic/1.1.11/ccedfbacef4a6515d2983e3f89ed753d5d4fb665/logback-classic-1.1.11.jar
            logger.debug("取得文件路径[{}]", resourceAbsolutePath);

            if (resourceAbsolutePath.startsWith("jar:")) resourceAbsolutePath = resourceAbsolutePath.substring("jar:".length());
            if (resourceAbsolutePath.startsWith("file:")) resourceAbsolutePath = resourceAbsolutePath.substring("file:".length());

            int jarSeparatorIndex = resourceAbsolutePath.lastIndexOf("!");
            String resourceBasePath;
            if (jarSeparatorIndex > -1) {
                resourceBasePath = resourceAbsolutePath.substring(0, jarSeparatorIndex);
            } else {
                //path end with /, eg:/Users/xiayx/Documents/Projects/peacetrue-velocity/build/classes/java/test/
                resourceBasePath = resourceAbsolutePath.substring(0, resourceAbsolutePath.length() - location.length() - 1);
            }
            logger.debug("取得文件基础路径[{}]", resourceBasePath);

            if (resourceBasePath.endsWith(".jar")) {
                JarInputStream jarInputStream = new JarInputStream(new FileInputStream(resourceBasePath));
                JarEntry jarEntry;
                while ((jarEntry = jarInputStream.getNextJarEntry()) != null) {
                    String entryName = jarEntry.getName();
                    if (entryName.endsWith("/")) entryName = entryName.substring(0, entryName.length() - 1);
                    logger.trace("读取jar包[{}]中的文件[{}]", resourceBasePath, entryName);
                    if (entryName.startsWith(location)) {
                        logger.debug("在jar包[{}]中找到匹配的文件[{}]", resourceBasePath, entryName);
                        files.add(new ClasspathResource(entryName, jarEntry.isDirectory(), classLoader));
                    }
                }
            } else {
                Path resourceAbsolutePathObject = Paths.get(resourceAbsolutePath);
                Files.walkFileTree(resourceAbsolutePathObject, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                        files.add(new ResourceImpl(dir.toString().substring(resourceBasePath.length() + 1), true));
                        return super.preVisitDirectory(dir, attrs);
                    }

                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        files.add(new ResourceImpl(file.toString().substring(resourceBasePath.length() + 1), false, new FileInputStream(file.toFile())));
                        return super.visitFile(file, attrs);
                    }
                });
            }
        }
        return files;
    }

    public static String format(String classpath) {
        if (classpath.startsWith("/")) classpath = classpath.substring(1);
        if (classpath.endsWith("/")) classpath = classpath.substring(0, classpath.length() - 1);
        return classpath;
    }
}
