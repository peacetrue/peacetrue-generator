package com.github.peacetrue.generator.resourceloader;

import javax.annotation.Nullable;
import java.io.InputStream;
import java.util.Objects;

/**
 * 类路径资源
 *
 * @author xiayx
 */
public class ClasspathResource extends AbstractResource {

    private ClassLoader classLoader;

    public ClasspathResource(String path, boolean isDirectory) {
        this(path, isDirectory, ClassLoader.getSystemClassLoader());
    }

    public ClasspathResource(String path, boolean isDirectory, ClassLoader classLoader) {
        super(path, isDirectory);
        this.classLoader = Objects.requireNonNull(classLoader);
    }

    @Nullable
    @Override
    public InputStream getInputStream() {
        return classLoader.getResourceAsStream(getPath());
    }
}
