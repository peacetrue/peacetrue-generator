package com.github.peacetrue.generator.placeholder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 占位符解析器实现
 *
 * @author xiayx
 */
public class PlaceholderResolverImpl implements PlaceholderResolver {

    /** 使用 {@code {}} 作为占位符 */
    public static final PlaceholderResolverImpl DEFAULT = new PlaceholderResolverImpl();

    private Logger logger = LoggerFactory.getLogger(getClass());

    /** 解析规则 */
    private Pattern pattern;
    /** 占位符对应的实际值转换器 */
    private BiFunction<String, String, String> converter;

    private PlaceholderResolverImpl() {
        this("{", "}");
    }

    public PlaceholderResolverImpl(String prefix, String postfix) {
        this(prefix, postfix, (placeholder, actualValue) -> actualValue);
    }

    public PlaceholderResolverImpl(BiFunction<String, String, String> converter) {
        this("{", "}", converter);
    }

    public PlaceholderResolverImpl(String prefix, String postfix, BiFunction<String, String, String> converter) {
        this.pattern = Pattern.compile(String.format("%s(.*?)%s", Pattern.quote(prefix), Pattern.quote(postfix)));
        this.converter = Objects.requireNonNull(converter);
    }

    @Override
    public String resolve(String string, Map<String, Object> context) {
        logger.info("解析字符串[{}]", string);
        Matcher matcher = pattern.matcher(string);
        StringBuffer resolved = new StringBuffer();
        while (matcher.find()) {
            String placeholder = matcher.group(1);
            String message = String.format("占位符[%s]对应的实际值不存在", placeholder);
            String actualValue = Objects.requireNonNull(context.get(placeholder), message).toString();
            logger.debug("取得占位符[{}]对应的实际值[{}]", placeholder, actualValue);
            matcher.appendReplacement(resolved, converter.apply(placeholder, actualValue));
        }
        matcher.appendTail(resolved);
        return resolved.toString();
    }
}
