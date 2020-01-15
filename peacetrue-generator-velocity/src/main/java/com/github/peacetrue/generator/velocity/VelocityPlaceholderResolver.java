package com.github.peacetrue.generator.velocity;

import com.github.peacetrue.generator.placeholder.PlaceholderResolver;
import lombok.Setter;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;
import java.util.Map;

/**
 * Velocity 占位符解析器
 *
 * @author xiayx
 */
@Setter
public class VelocityPlaceholderResolver implements PlaceholderResolver {

    private VelocityEngine velocityEngine;
    private ContextFactory contextFactory;

    @Override
    public String resolve(String placeholder, Map<String, Object> context) {
        StringWriter stringWriter = new StringWriter();
        velocityEngine.evaluate(contextFactory.getContext(context), stringWriter, getClass().getName(), placeholder);
        return stringWriter.toString();
    }
}
