package com.sky.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 七牛对象存储配置
 *
 * @author ZJHAO
 * @date 2024/06/14
 */
@Component
@ConfigurationProperties(prefix = "sky.qiniukodo")
@Data
public class QiniuKodoProperties {

    // private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String cdnUrl;
}
