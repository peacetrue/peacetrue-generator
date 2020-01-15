package com.github.peacetrue.generator.velocity;

import org.apache.velocity.context.Context;

import java.util.Map;

/**
 * 上下文工厂
 *
 * @author xiayx
 */
public interface ContextFactory {

    /**
     * 获取上下文
     *
     * @param context 上下文
     * @return velocity 上下文
     */
    Context getContext(Map<String, Object> context);
}
