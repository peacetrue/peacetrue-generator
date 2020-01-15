package com.github.peacetrue.generator;

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
            context.put(path, value.toString().replaceAll("\\.", "/"));
        });
    }
}
