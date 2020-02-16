package com.github.peacetrue.generator;

import java.util.Map;

/**
 * 上下文处理器
 *
 * @author xiayx
 */
public interface ContextHandler {

    /**
     * 处理上下文
     *
     * @param context 模板参数
     */
    void handle(Map<String, Object> context);

}
