package com.github.peacetrue.generator.resourceloader;

import java.io.IOException;
import java.util.List;

/**
 * 资源加载器
 *
 * @author xiayx
 */
public interface ResourceLoader {

    /**
     * 获取 {@code location} 文件
     *
     * @param location 文件地址
     * @return 资源
     * @throws IOException 获取文件时发生异常
     */
    default Resource getResource(String location) throws IOException {
        return getResources(location).get(0);
    }

    /**
     * 获取 {@code location} 目录地址下的所有文件
     *
     * @param location 目录地址
     * @return 资源集合
     * @throws IOException 获取文件时发生异常
     */
    List<Resource> getResources(String location) throws IOException;

}
