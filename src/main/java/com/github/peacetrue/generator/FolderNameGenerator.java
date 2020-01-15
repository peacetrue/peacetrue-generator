package com.github.peacetrue.generator;

import com.github.peacetrue.generator.placeholder.PlaceholderResolver;
import com.github.peacetrue.generator.placeholder.PlaceholderResolverImpl;
import com.github.peacetrue.generator.resourceloader.ClasspathResourceLoader;
import com.github.peacetrue.generator.resourceloader.FileSystemResourceLoader;
import com.github.peacetrue.generator.resourceloader.Resource;
import com.github.peacetrue.generator.resourceloader.ResourceLoader;
import com.github.peacetrue.util.FileUtils;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 目录名称生成器
 *
 * @author xiayx
 */
@Getter
@Setter
public class FolderNameGenerator extends AbstractGenerator {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private ResourceLoader resourceLoader;
    private PlaceholderResolver placeholderResolver;

    public FolderNameGenerator() {
        this(FileSystemResourceLoader.DEFAULT);
    }

    public FolderNameGenerator(ResourceLoader resourceLoader) {
        this(resourceLoader, new PlaceholderResolverImpl(
                (placeholder, actualValue) -> actualValue.replaceAll("\\.", "/")
        ));
    }

    public FolderNameGenerator(ResourceLoader resourceLoader, PlaceholderResolver placeholderResolver) {
        this.resourceLoader = Objects.requireNonNull(resourceLoader);
        this.placeholderResolver = Objects.requireNonNull(placeholderResolver);
    }

    @Override
    public void generate(Map<String, Object> context) throws IOException {
        logger.info("根据模板目录[{}]生成目标[{}]", getSourcePath(), getTargetPath());
        List<Resource> resources = resourceLoader.getResources(getSourcePath());
        logger.debug("读取模板[{}]下共[{}]个文件", getSourcePath(), resources.size());
        for (Resource resource : resources) {
            if (resource.getPath().equals(getSourcePath())) continue;
            String resolvedPath = placeholderResolver.resolve(resource.getPath(), context);
            logger.debug("取得模板文件[{}]的解析路径[{}]", resource, resolvedPath);
            Path targetFilePath = Paths.get(getTargetPath(), resolvedPath.substring(getSourcePath().length()));
            if (resource.isDirectory()) {
                FileUtils.createFolderIfAbsent(targetFilePath);
            } else {
                FileUtils.createFileIfAbsent(targetFilePath);
                InputStream inputStream = resource.getInputStream();
                Files.copy(Objects.requireNonNull(inputStream), targetFilePath, StandardCopyOption.REPLACE_EXISTING);
                inputStream.close();
            }
        }
    }

}
