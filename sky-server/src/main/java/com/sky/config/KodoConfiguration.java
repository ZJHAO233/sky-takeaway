package com.sky.config;

import com.sky.properties.QiniuKodoProperties;
import com.sky.utils.QiniuKodoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类，用于创建QiniuKodoUtil对象
 *
 * @author ZJHAO
 * @date 2024/06/14
 */
@Configuration
@Slf4j
public class KodoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public QiniuKodoUtil qiniuKodoUtil(QiniuKodoProperties qiniuKodoProperties) {
        log.info("开始创建七牛文件上传工具类对象：{}", qiniuKodoProperties);
        return new QiniuKodoUtil(
                qiniuKodoProperties.getAccessKey(),
                qiniuKodoProperties.getSecretKey(),
                qiniuKodoProperties.getBucket()
        );
    }
}
