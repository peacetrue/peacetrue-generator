package com.github.peacetrue.generator.velocity;

import com.github.peacetrue.generator.AbstractFolderGenerator;
import lombok.Getter;
import lombok.Setter;
import org.apache.velocity.app.VelocityEngine;

import java.io.*;
import java.util.Map;

/**
 * Velocity 目录生成器
 *
 * @author xiayx
 */
@Setter
@Getter
public class VelocityFolderGenerator extends AbstractFolderGenerator {

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
