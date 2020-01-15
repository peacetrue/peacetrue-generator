package com.github.peacetrue.generator;

import com.github.peacetrue.generator.placeholder.PlaceholderResolver;
import com.github.peacetrue.generator.placeholder.PlaceholderResolverImpl;
import com.github.peacetrue.generator.resourceloader.ClasspathResourceLoader;
import com.github.peacetrue.generator.resourceloader.FileSystemResourceLoader;
import com.github.peacetrue.generator.resourceloader.ResourceLoader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 生成器自动配置
 *
 * @author xiayx
 */
@Configuration
@EnableConfigurationProperties(GeneratorProperties.class)
public class GeneratorAutoConfiguration {

    /** 文件系统资源加载器 */
    public static final String RESOURCE_LOADER_FILE_SYSTEM = "fileSystemResourceLoader";
    /** 类路径资源加载器 */
    public static final String RESOURCE_LOADER_CLASSPATH = "classpathResourceLoader";
    /** 目录名称生成器 */
    public static final String GENERATOR_FOLDER_NAME = "folderNameGenerator";
    /** 目录内容生成器 */
    public static final String GENERATOR_FOLDER_CONTENT = "folderContentGenerator";
    /** 目录生成器（含目录名称生成器和目录内容生成器的功能） */
    public static final String GENERATOR_FOLDER = "folderGenerator";
    /** 文件生成器 */
    public static final String GENERATOR_FILE = "fileGenerator";

    private GeneratorProperties properties;

    public GeneratorAutoConfiguration(GeneratorProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean(name = RESOURCE_LOADER_FILE_SYSTEM)
    public FileSystemResourceLoader fileSystemResourceLoader() {
        return FileSystemResourceLoader.DEFAULT;
    }

    @Bean
    @ConditionalOnMissingBean(name = RESOURCE_LOADER_CLASSPATH)
    public ClasspathResourceLoader classpathResourceLoader() {
        return new ClasspathResourceLoader();
    }

    @Bean
    @ConditionalOnMissingBean(PlaceholderResolver.class)
    public PlaceholderResolver placeholderResolver() {
        return PlaceholderResolverImpl.DEFAULT;
    }

    @Bean
    @ConditionalOnMissingBean(name = GENERATOR_FOLDER_NAME)
    public FolderNameGenerator folderNameGenerator(@Qualifier(RESOURCE_LOADER_FILE_SYSTEM) ResourceLoader fileSystemResourceLoader,
                                                   PlaceholderResolver placeholderResolver) {
        FolderNameGenerator folderNameGenerator = new FolderNameGenerator(fileSystemResourceLoader, placeholderResolver);
        folderNameGenerator.setSourcePath(properties.getSourcePath());
        folderNameGenerator.setTargetPath(properties.getTargetPath());
        return folderNameGenerator;
    }

}
