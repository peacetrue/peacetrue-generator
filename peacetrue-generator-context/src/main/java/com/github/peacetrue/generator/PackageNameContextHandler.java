package com.github.peacetrue.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * 包名上下文处理器，将包名转换为路径
 * <p>
 * com.github.peacetrue.generator
 * ->
 * com/github/peacetrue/generator
 *
 * @author xiayx
 */
public class PackageNameContextHandler implements ContextHandler {

    public static final PackageNameContextHandler DEFAULT = new PackageNameContextHandler();

    private Logger logger = LoggerFactory.getLogger(getClass());

    private String name;
    private String path;

    private PackageNameContextHandler() {
        this("basePackageName", "basePackagePath");
    }

    public PackageNameContextHandler(String name, String path) {
        this.name = Objects.requireNonNull(name);
        this.path = Objects.requireNonNull(path);
    }

    @Override
    public void handle(Map<String, Object> context) {
        Optional.ofNullable(context.get(name)).ifPresent(value -> {
            String pathValue = value.toString().replaceAll("\\.", "/");
            logger.debug("根据上下文变量[{}]=[{}]设置路径路径[{}]=[{}]", name, value, path, pathValue);
            context.put(path, pathValue);
        });
    }
}
