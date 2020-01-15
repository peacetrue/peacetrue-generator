package com.github.peacetrue.generator;

import java.util.Map;

/**
 * 上下文处理器
 *
 * @author xiayx
 */
public interface ContextHandler {

    /** 处理上下文 */
    void handle(Map<String, Object> context);

}
