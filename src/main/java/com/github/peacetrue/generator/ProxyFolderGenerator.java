package com.github.peacetrue.generator;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

/**
 * 目录生成器
 *
 * @author xiayx
 */
@Getter
@Setter
public class ProxyFolderGenerator implements Generator {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /** 目录内容生成器 */
    private AbstractGenerator folderContentGenerator;
    /** 目录名称生成器 */
    private AbstractGenerator folderNameGenerator;
    /** 上下文处理器 */
    private List<ContextHandler> contextHandlers;

    @Override
    public void generate(Map<String, Object> context) throws IOException {
        logger.info("使用目录生成器生成目标文件");

        if (contextHandlers != null) contextHandlers.forEach(contextHandler -> contextHandler.handle(context));

        folderContentGenerator.generate(context);

        logger.debug("将目标文件从[{}]移动至[{}]", folderContentGenerator.getTargetPath(), folderNameGenerator.getSourcePath());
        Files.move(Paths.get(folderContentGenerator.getTargetPath()), Paths.get(folderNameGenerator.getSourcePath()), StandardCopyOption.REPLACE_EXISTING);

        folderNameGenerator.generate(context);

        logger.debug("删除临时文件[{}]", folderNameGenerator.getSourcePath());
        FileSystemUtils.deleteRecursively(new File(folderNameGenerator.getSourcePath()));
    }

    public void setSourcePath(String sourcePath) {
        folderContentGenerator.setSourcePath(sourcePath);
    }

    public void setTargetPath(String targetPath) {
        folderContentGenerator.setTargetPath(targetPath);
        String sourcePath = new StringBuilder(targetPath).insert(targetPath.lastIndexOf("/") + 1, ".").toString();
        folderNameGenerator.setSourcePath(sourcePath);
        folderNameGenerator.setTargetPath(targetPath);
    }

}
