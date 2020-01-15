package com.github.peacetrue.generator.velocity;

import com.github.peacetrue.generator.AbstractFolderContextGenerator;
import lombok.Getter;
import lombok.Setter;
import org.apache.velocity.app.VelocityEngine;

import java.io.*;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Velocity 目录生成器
 *
 * @author xiayx
 */
@Getter
@Setter
public class VelocityFolderContentGenerator extends AbstractFolderContextGenerator {

    public static final Predicate<String> IGNORED_CONVENTION = path -> path.startsWith("include") || path.endsWith(".vtl") || path.endsWith("Placeholder.adoc");
    public static final Predicate<String> PARSED_CONVENTION = path -> path.endsWith(".vm");
    public static final Function<String, String> CONVERTER_CONVENTION = path -> path.endsWith(".vm") ? path.substring(0, path.length() - 3) : path;

    private VelocityEngine velocityEngine;
    private ContextFactory contextFactory = VelocityContextFactory.DEFAULT;

    @Override
    protected void generate(Map<String, Object> context, InputStream sourceInputStream, OutputStream targetOutputStream) throws IOException {
        InputStreamReader reader = new InputStreamReader(sourceInputStream);
        OutputStreamWriter writer = new OutputStreamWriter(targetOutputStream);
        velocityEngine.evaluate(contextFactory.getContext(context), writer, getClass().getName(), reader);
        writer.flush();
        writer.close();
        reader.close();
    }
}
