package com.github.peacetrue.generator.plugin;

import com.github.peacetrue.generator.Generator;
import com.github.peacetrue.generator.GeneratorAutoConfiguration;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.DefaultResourceLoader;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 生成器客户端
 *
 * @author xiayx
 */
@Setter
@Getter
@SpringBootApplication
public class GeneratorClient {

    private Logger thisLogger = LoggerFactory.getLogger(getClass());

    /** 配置信息 */
    private Map<String, Object> properties;
    /** 上下文 */
    private Map<String, Object> context;

    public void generate() throws IOException {
        thisLogger.info("生成目标文件");

        SpringApplication springApplication = new SpringApplication(new DefaultResourceLoader(), GeneratorContextAutoConfiguration.class);
        springApplication.setWebEnvironment(false);
        springApplication.setEnvironment(new StandardEnvironment() {
            @Override
            protected void customizePropertySources(MutablePropertySources propertySources) {
                super.customizePropertySources(propertySources);
                //logger 已经在 AbstractEnvironment.Log 中定义了，所以改为 thisLogger
                if (properties != null) {
                    thisLogger.debug("添加配置信息[{}]", properties);
                    propertySources.addLast(new MapPropertySource("generator.properties", properties));
                }
            }
        });

        ConfigurableApplicationContext applicationContext = springApplication.run();
        Generator folderGenerator = applicationContext.getBean(GeneratorAutoConfiguration.GENERATOR_FOLDER, Generator.class);
        thisLogger.debug("取得目录生成器[{}]", folderGenerator);

        ContextsSupplier contextsSupplier = applicationContext.getBean(ContextsSupplier.class);
        List<Map<String, Object>> contexts = contextsSupplier.getContexts();
        if (this.context == null) this.context = Collections.emptyMap();
        if (contexts.isEmpty()) {
            folderGenerator.generate(context);
        } else {
            for (Map<String, Object> context : contexts) {
                context.putAll(this.context);//TODO 优先级考虑
                folderGenerator.generate(context);
            }
        }
        applicationContext.close();
    }

}
