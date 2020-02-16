package com.github.peacetrue.generator.plugin;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

public class GeneratorContextAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ContextsSupplier contextsSupplier() {
        return ContextsSupplierImpl.DEFAULT;
    }
}
