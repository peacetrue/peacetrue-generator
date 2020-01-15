package com.github.peacetrue.generator;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 生成器属性
 *
 * @author xiayx
 */
@Data
@ConfigurationProperties(prefix = "peacetrue.generator")
public class GeneratorProperties {

    /** {@link com.github.peacetrue.generator.AbstractGenerator#sourcePath} */
    private String sourcePath;
    /** {@link com.github.peacetrue.generator.AbstractGenerator#targetPath} */
    private String targetPath;

}
