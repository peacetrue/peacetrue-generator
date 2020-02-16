package com.github.peacetrue.generator;

import com.github.peacetrue.generator.resourceloader.Resource;
import com.github.peacetrue.generator.resourceloader.ResourceLoader;
import com.github.peacetrue.util.FileUtils;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 抽象的目录生成器
 *
 * @author xiayx
 */
@Setter
@Getter
public abstract class AbstractFolderGenerator extends AbstractGenerator {

    /** 文件加载器：加载目录下所有文件集合 */
    private ResourceLoader resourceLoader;
    /** 忽略模板文件断言：true则忽略此模板文件 */
    private Predicate<String> ignoredPredicate;
    /** 解析模板文件断言：true则解析此模板文件，否则拷贝 */
    private Predicate<String> parsedPredicate;
    /** 目标文件转换器：根据源文件路径产生目标文件路径 */
    private Function<String, String> targetPathHandler;
    /** 上下文处理器 */
    private List<ContextHandler> contextHandlers;

    @Override
    public void generate(Map<String, Object> context) throws IOException {
        Objects.requireNonNull(getSourcePath());
        Objects.requireNonNull(getTargetPath());
        logger.info("根据模板目录[{}]生成目标文件", getSourcePath());

        if (contextHandlers != null) contextHandlers.forEach(contextHandler -> contextHandler.handle(context));

        List<Resource> resources = resourceLoader.getResources(getSourcePath());
        logger.debug("取得模板目录[{}]下共[{}]个文件", getSourcePath(), resources.size());

        Path targetPathObject = Paths.get(getTargetPath());
        for (Resource resource : resources) {
            //忽略根目录
            if (resource.getPath().equals(getSourcePath())) continue;

            String relativeSourcePath = resource.getPath().substring(getSourcePath().length() + 1);
            if (ignoredPredicate.test(relativeSourcePath)) {
                logger.debug("忽略模板文件[{}]", resource);
                continue;
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            this.generate(context, new ByteArrayInputStream(relativeSourcePath.getBytes(StandardCharsets.UTF_8)), outputStream);
            relativeSourcePath = outputStream.toString(StandardCharsets.UTF_8.name());

            Path targetFilePath = targetPathObject.resolve(targetPathHandler.apply(relativeSourcePath));
            logger.debug("根据模板文件[{}]生成目标文件[{}]", resource.getPath(), targetFilePath);
            if (resource.isDirectory()) {
                FileUtils.createFolderIfAbsent(targetFilePath);
            } else {
                FileUtils.createFileIfAbsent(targetFilePath);
                InputStream inputStream = Objects.requireNonNull(resource.getInputStream());
                if (parsedPredicate.test(relativeSourcePath)) {
                    logger.debug("解析模板文件[{}]", resource.getPath());
                    this.generate(context, inputStream, new FileOutputStream(targetFilePath.toFile()));
                } else {
                    logger.debug("拷贝模板文件[{}]", resource.getPath());
                    Files.copy(inputStream, targetFilePath, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
    }

    protected abstract void generate(Map<String, Object> context, InputStream sourceInputStream, OutputStream targetOutputStream) throws IOException;
}
