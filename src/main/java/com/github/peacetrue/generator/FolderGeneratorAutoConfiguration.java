package com.github.peacetrue.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 目录生成器自动配置
 *
 * @author xiayx
 */
//@Configuration
//@ConditionalOnBean(name = {
//        GeneratorAutoConfiguration.GENERATOR_FOLDER_CONTENT,
//        GeneratorAutoConfiguration.GENERATOR_FOLDER_NAME
//})
//@AutoConfigureAfter(GeneratorAutoConfiguration.class)
public class FolderGeneratorAutoConfiguration {

    @Autowired
    private GeneratorProperties properties;

    @Bean
    @ConditionalOnMissingBean(name = GeneratorAutoConfiguration.GENERATOR_FOLDER)
    public ProxyFolderGenerator folderGenerator(@Qualifier(GeneratorAutoConfiguration.GENERATOR_FOLDER_CONTENT) AbstractGenerator folderContentGenerator,
                                                @Qualifier(GeneratorAutoConfiguration.GENERATOR_FOLDER_NAME) AbstractGenerator folderNameGenerator,
                                                @Autowired(required = false) List<ContextHandler> contextHandlers) {
        ProxyFolderGenerator folderGenerator = new ProxyFolderGenerator();
        folderGenerator.setFolderContentGenerator(folderContentGenerator);
        folderGenerator.setFolderNameGenerator(folderNameGenerator);
        folderGenerator.setContextHandlers(contextHandlers);
        folderGenerator.setSourcePath(properties.getSourcePath());
        folderGenerator.setTargetPath(properties.getTargetPath());
        return folderGenerator;
    }
}
