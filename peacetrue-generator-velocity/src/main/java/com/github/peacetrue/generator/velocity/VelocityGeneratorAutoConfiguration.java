package com.github.peacetrue.generator.velocity;

import com.github.peacetrue.generator.ContextHandler;
import com.github.peacetrue.generator.GeneratorAutoConfiguration;
import com.github.peacetrue.generator.GeneratorProperties;
import com.github.peacetrue.generator.resourceloader.ResourceLoader;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.github.peacetrue.generator.GeneratorAutoConfiguration.*;

/**
 * Velocity生成器自动配置
 *
 * @author xiayx
 */
@Configuration
@AutoConfigureAfter(GeneratorAutoConfiguration.class)
public class VelocityGeneratorAutoConfiguration {

    @Autowired
    private GeneratorProperties properties;

    public VelocityGeneratorAutoConfiguration() {
    }

    @Bean
    @ConditionalOnMissingBean(name = "classpathVelocityEngine")
    public VelocityEngine classpathVelocityEngine() {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADERS, "classpath");
        velocityEngine.setProperty("resource.loader.classpath.class", ClasspathResourceLoader.class.getName());
        velocityEngine.setProperty(RuntimeConstants.PARSER_HYPHEN_ALLOWED, true);
        velocityEngine.setProperty(RuntimeConstants.VM_LIBRARY, properties.getSourcePath() + "/" + RuntimeConstants.VM_LIBRARY_DEFAULT);
        return velocityEngine;
    }

    @Bean
    @ConditionalOnMissingBean(ContextFactory.class)
    public ContextFactory contextFactory() {
        return VelocityContextFactory.DEFAULT;
    }

    @Bean
    @ConditionalOnMissingBean(name = GENERATOR_FILE)
    public VelocityFileGenerator fileGenerator(@Qualifier("classpathVelocityEngine") VelocityEngine classpathVelocityEngine,
                                               ContextFactory contextFactory,
                                               @Qualifier(RESOURCE_LOADER_CLASSPATH) ResourceLoader classpathResourceLoader) {
        VelocityFileGenerator velocityGenerator = new VelocityFileGenerator();
        velocityGenerator.setVelocityEngine(classpathVelocityEngine);
        velocityGenerator.setContextFactory(contextFactory);
        velocityGenerator.setResourceLoader(classpathResourceLoader);
        velocityGenerator.setSourcePath(properties.getSourcePath());
        velocityGenerator.setTargetPath(properties.getTargetPath());
        return velocityGenerator;
    }

    @Bean
    @ConditionalOnMissingBean(name = "folderContentGeneratorIgnoredPredicate")
    public Predicate<String> folderContentGeneratorIgnoredPredicate() {
        return VelocityFolderContentGenerator.IGNORED_CONVENTION;
    }

    @Bean
    @ConditionalOnMissingBean(name = "folderContentGeneratorParsedPredicate")
    public Predicate<String> folderContentGeneratorParsedPredicate() {
        return VelocityFolderContentGenerator.PARSED_CONVENTION;
    }

    @Bean
    @ConditionalOnMissingBean(name = "folderContentGeneratorTargetPathHandler")
    public Function<String, String> folderContentGeneratorTargetPathHandler() {
        return VelocityFolderContentGenerator.CONVERTER_CONVENTION;
    }

    @Bean
    @ConditionalOnMissingBean(name = GENERATOR_FOLDER_CONTENT)
    public VelocityFolderContentGenerator folderContentGenerator(@Qualifier("classpathVelocityEngine") VelocityEngine classpathVelocityEngine,
                                                                 ContextFactory contextFactory,
                                                                 @Qualifier(RESOURCE_LOADER_CLASSPATH) ResourceLoader classpathResourceLoader,
                                                                 @Qualifier("folderContentGeneratorIgnoredPredicate") Predicate<String> folderContentGeneratorIgnoredPredicate,
                                                                 @Qualifier("folderContentGeneratorParsedPredicate") Predicate<String> folderContentGeneratorParsedPredicate,
                                                                 @Qualifier("folderContentGeneratorTargetPathHandler") Function<String, String> folderContentGeneratorTargetPathHandler) {
        VelocityFolderContentGenerator contentGenerator = new VelocityFolderContentGenerator();
        contentGenerator.setVelocityEngine(classpathVelocityEngine);
        contentGenerator.setContextFactory(contextFactory);
        contentGenerator.setResourceLoader(classpathResourceLoader);
        contentGenerator.setIgnoredPredicate(folderContentGeneratorIgnoredPredicate);
        contentGenerator.setParsedPredicate(folderContentGeneratorParsedPredicate);
        contentGenerator.setTargetPathHandler(folderContentGeneratorTargetPathHandler);
        return contentGenerator;
    }

    @Bean
    @ConditionalOnMissingBean(name = GENERATOR_FOLDER)
    public VelocityFolderGenerator folderGenerator(@Qualifier("classpathVelocityEngine") VelocityEngine classpathVelocityEngine,
                                                   ContextFactory contextFactory,
                                                   @Autowired(required = false) List<ContextHandler> contextHandlers,
                                                   @Qualifier(RESOURCE_LOADER_CLASSPATH) ResourceLoader classpathResourceLoader,
                                                   @Qualifier("folderContentGeneratorIgnoredPredicate") Predicate<String> folderContentGeneratorIgnoredPredicate,
                                                   @Qualifier("folderContentGeneratorParsedPredicate") Predicate<String> folderContentGeneratorParsedPredicate,
                                                   @Qualifier("folderContentGeneratorTargetPathHandler") Function<String, String> folderContentGeneratorTargetPathHandler) {
        VelocityFolderGenerator folderGenerator = new VelocityFolderGenerator();
        folderGenerator.setVelocityEngine(classpathVelocityEngine);
        folderGenerator.setContextFactory(contextFactory);
        folderGenerator.setContextHandlers(contextHandlers);
        folderGenerator.setResourceLoader(classpathResourceLoader);
        folderGenerator.setIgnoredPredicate(folderContentGeneratorIgnoredPredicate);
        folderGenerator.setParsedPredicate(folderContentGeneratorParsedPredicate);
        folderGenerator.setTargetPathHandler(folderContentGeneratorTargetPathHandler);
        folderGenerator.setSourcePath(properties.getSourcePath());
        folderGenerator.setTargetPath(properties.getTargetPath());
        return folderGenerator;
    }

}
