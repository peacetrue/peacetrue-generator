package com.github.peacetrue.generator.placeholder;

import java.util.Map;

/**
 * 占位符解析器
 *
 * @author xiayx
 */
public interface PlaceholderResolver {

    /**
     * 解析占位符
     *
     * @param placeholder 含有占位符的字符串
     * @param context     占位符对应的值
     * @return 实际字符串
     */
    String resolve(String placeholder, Map<String, Object> context);

}
