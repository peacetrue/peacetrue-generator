package com.github.peacetrue.generator.placeholder;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiayx
 */
public class PlaceholderResolverImplTest {


    @Test
    public void resolve() {
        Map<String, Object> context = new HashMap<>();
        context.put("teacher", "王老师");
        context.put("student", "小明同学");
        String resolved = PlaceholderResolverImpl.DEFAULT.resolve("{teacher}教{student}", context);
        Assert.assertEquals(context.get("teacher") + "教" + context.get("student"), resolved);
    }
}