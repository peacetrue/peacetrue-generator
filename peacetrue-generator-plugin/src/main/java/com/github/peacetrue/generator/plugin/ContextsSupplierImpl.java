package com.github.peacetrue.generator.plugin;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ContextsSupplierImpl implements ContextsSupplier {

    public static final ContextsSupplierImpl DEFAULT = new ContextsSupplierImpl();

    private List<Map<String, Object>> contexts;

    private ContextsSupplierImpl() {
        this(Collections.emptyList());
    }

    public ContextsSupplierImpl(List<Map<String, Object>> contexts) {
        this.contexts = Objects.requireNonNull(contexts);
    }

    @Override
    public List<Map<String, Object>> getContexts() {
        return contexts;
    }
}
