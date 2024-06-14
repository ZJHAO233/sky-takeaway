package com.sky.config;

import com.sky.properties.AliOssProperties;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类，用于创建AliOssUtil对象
 *
 * @author ZJHAO
 * @date 2024/06/14
 */
@Configuration
@Slf4j
public class OssConfiguration {

    @Bean
    // 这个注解表示只有在当前应用程序上下文中不存在名为AliOssUtil的Bean时，
    // 才会创建并注册这个Bean。
    // 这样可以避免在配置文件中多次配置相同的Bean
    @ConditionalOnMissingBean
    public AliOssUtil aliossUtil(AliOssProperties aliOssProperties) {
        log.info("开始创建阿里云文件上传工具类对象：{}", aliOssProperties);
        return new AliOssUtil(aliOssProperties.getEndpoint(),
                aliOssProperties.getAccessKeyId(),
                aliOssProperties.getAccessKeySecret(),
                aliOssProperties.getBucketName());
    }
}
