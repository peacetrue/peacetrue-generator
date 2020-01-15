package com.github.peacetrue.generator.velocity;

import org.apache.velocity.context.Context;
import org.apache.velocity.tools.ToolContext;
import org.apache.velocity.tools.ToolManager;
import org.apache.velocity.tools.config.ConfigurationUtils;

import java.util.Map;
import java.util.Objects;

/**
 * {@link org.apache.velocity.tools.ToolContext} 工厂
 *
 * @author xiayx
 */
public class ToolContextFactory implements ContextFactory {

    public static final ToolContextFactory DEFAULT = new ToolContextFactory();

    private ToolManager manager = new ToolManager();
    private Map<String, Object> context;

    private ToolContextFactory() {
        this(ConfigurationUtils.AUTOLOADED_XML_PATH);
    }

    public ToolContextFactory(String path) {
        this.manager.configure(Objects.requireNonNull(path));
    }

    public ToolContextFactory(String path, Map<String, Object> context) {
        this(path);
        this.context = Objects.requireNonNull(context);
    }

    public ToolContextFactory(Map<String, Object> context) {
        this(ConfigurationUtils.AUTOLOADED_XML_PATH, context);
    }

    @Override
    public Context getContext(Map<String, Object> context) {
        ToolContext toolContext = manager.createContext(this.context);
        toolContext.putAll(context);
        return toolContext;
    }

}
