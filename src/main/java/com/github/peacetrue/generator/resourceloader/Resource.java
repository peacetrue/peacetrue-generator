package com.github.peacetrue.generator.resourceloader;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;

/**
 * 资源
 *
 * @author xiayx
 */
public interface Resource {

    /** 获取文件路径 */
    String getPath();

    /** 是否目录 */
    boolean isDirectory();

    /** 获取输入流，目录时为 {@code null} */
    @Nullable
    InputStream getInputStream() throws IOException;

}
