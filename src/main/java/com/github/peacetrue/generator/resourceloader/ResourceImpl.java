package com.github.peacetrue.generator.resourceloader;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.annotation.Nullable;
import java.io.InputStream;

/**
 * 资源实现
 *
 * @author xiayx
 */
@Setter
@Getter
@ToString(callSuper = true)
@NoArgsConstructor
public class ResourceImpl extends AbstractResource {

    /** 输入流，目录时为 {@code null} */
    @Nullable
    private InputStream inputStream;

    public ResourceImpl(String path, boolean isDirectory) {
        super(path, isDirectory);
    }

    public ResourceImpl(String path, boolean isDirectory, @Nullable InputStream inputStream) {
        super(path, isDirectory);
        this.inputStream = inputStream;
    }

}
