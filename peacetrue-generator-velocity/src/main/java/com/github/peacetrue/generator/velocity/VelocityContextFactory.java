package com.github.peacetrue.generator.velocity;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;

import java.util.Map;

/**
 * Velocity 上下文工厂
 *
 * @author xiayx
 */
public class VelocityContextFactory implements ContextFactory {

    public static final VelocityContextFactory DEFAULT = new VelocityContextFactory();

    private VelocityContextFactory() {
    }

    @Override
    public Context getContext(Map<String, Object> context) {
        return new VelocityContext(context);
    }

}
