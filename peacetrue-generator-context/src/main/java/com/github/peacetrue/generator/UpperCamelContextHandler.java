package com.github.peacetrue.generator;

import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Function;

/**
 * 命名约定 转换器
 *
 * @author xiayx
 */
public class UpperCamelContextHandler implements ContextHandler {

    private static final Converter<String, String> TO_LOWER_CAMEL = CaseFormat.UPPER_CAMEL.converterTo(CaseFormat.LOWER_CAMEL);
    private static final Converter<String, String> TO_LOWER_HYPHEN = CaseFormat.UPPER_CAMEL.converterTo(CaseFormat.LOWER_HYPHEN);
    private static final Converter<String, String> TO_LOWER_UNDERSCORE = CaseFormat.UPPER_CAMEL.converterTo(CaseFormat.LOWER_UNDERSCORE);
    private static final List<Function<String, String>> CONVERTERS = Arrays.asList(
            TO_LOWER_CAMEL, TO_LOWER_HYPHEN, TO_LOWER_UNDERSCORE, String::toUpperCase, String::toLowerCase
    );

    private Logger logger = LoggerFactory.getLogger(getClass());
    private Set<String> keys = new HashSet<>();

    public UpperCamelContextHandler(Set<String> keys) {
        this.keys.addAll(keys);
    }

    public UpperCamelContextHandler(String... keys) {
        this.keys.addAll(Arrays.asList(keys));
    }

    @Override
    public void handle(Map<String, Object> context) {
        for (String key : keys) {
            Object value = context.get(key);
            if (value == null) {
                logger.trace("Ignore keys [{}] with null values", key);
                continue;
            }
            for (Function<String, String> converter : CONVERTERS) {
                String newKey = converter.apply(key);
                String newValue = converter.apply((String) value);
                logger.debug("添加[{}]=[{}]", newKey, newValue);
                context.put(newKey, newValue);
            }
        }
    }
}
