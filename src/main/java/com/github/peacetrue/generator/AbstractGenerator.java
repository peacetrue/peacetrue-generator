package com.github.peacetrue.generator;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 抽象生成器
 *
 * @author xiayx
 */
@Getter
@Setter
public abstract class AbstractGenerator implements Generator {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    /** 模板文件路径：原路径 */
    private String sourcePath;
    /** 生成文件路径：目标路径 */
    private String targetPath;

}
