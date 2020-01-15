package com.github.peacetrue.generator.velocity;

import com.github.peacetrue.generator.AbstractGenerator;
import com.github.peacetrue.generator.resourceloader.Resource;
import com.github.peacetrue.generator.resourceloader.ResourceLoader;
import com.github.peacetrue.util.FileUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.velocity.app.VelocityEngine;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;

/**
 * Velocity 文件内容生成器
 *
 * @author xiayx
 */
@Getter
@Setter
public class VelocityFileGenerator extends AbstractGenerator {

    private VelocityEngine velocityEngine;
    private ResourceLoader resourceLoader;
    private ContextFactory contextFactory = VelocityContextFactory.DEFAULT;

    @Override
    public void generate(Map<String, Object> context) throws IOException {
        logger.info("根据模板目录[{}]生成目标文件[{}]", getSourcePath(), getTargetPath());
        Path targetPathObject = Paths.get(getTargetPath());
        FileUtils.createFileIfAbsent(targetPathObject);
        Resource resource = resourceLoader.getResource(getSourcePath());
        FileWriter writer = new FileWriter(targetPathObject.toFile());
        InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(resource.getInputStream()));
        velocityEngine.evaluate(contextFactory.getContext(context), writer, getClass().getName(), reader);
        writer.flush();
        writer.close();
        reader.close();
    }
}
