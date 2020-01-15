package com.github.peacetrue.generator.resourceloader;

import lombok.*;

/**
 * 抽象的资源
 *
 * @author xiayx
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractResource implements Resource {

    /** 文件路径 */
    private String path;
    /** 是否目录 */
    private boolean isDirectory;

}
