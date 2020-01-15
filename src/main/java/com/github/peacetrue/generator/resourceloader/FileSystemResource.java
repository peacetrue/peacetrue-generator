package com.github.peacetrue.generator.resourceloader;

import lombok.ToString;

import javax.annotation.Nullable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件系统资源
 *
 * @author xiayx
 */
@ToString(callSuper = true)
public class FileSystemResource extends AbstractResource {

    public FileSystemResource(String path, boolean isDirectory) {
        super(path, isDirectory);
    }

    @Nullable
    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(getPath());
    }

}
