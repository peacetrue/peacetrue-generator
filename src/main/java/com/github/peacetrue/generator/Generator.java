package com.github.peacetrue.generator;

import java.io.IOException;
import java.util.Map;

/**
 * 生成器
 *
 * @author xiayx
 * @see AbstractGenerator
 * @see FolderNameGenerator
 * @see AbstractFolderContextGenerator
 * @see ProxyFolderGenerator
 */
public interface Generator {

    /**
     * 生成目标物
     *
     * @param context 上下文变量
     * @throws IOException 生成过程中出现异常
     */
    void generate(Map<String, Object> context) throws IOException;

}
