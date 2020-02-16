package com.github.peacetrue.generator.plugin;

import java.util.List;
import java.util.Map;

/**
 * 上下文提供者
 *
 * @author xiayx
 */
public interface ContextsSupplier {

    /** 获取上下文 */
    List<Map<String, Object>> getContexts();

}
