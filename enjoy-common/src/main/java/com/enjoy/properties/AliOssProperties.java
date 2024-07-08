package com.enjoy.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 阿里云OSS配置
 */
@Component
@ConfigurationProperties(prefix = "enjoy.alioss")
@Data
public class AliOssProperties {
    
    private String endpoint = "oss-cn-hangzhou.aliyuncs.com";
    
    private String accessKeyId = "LTAI5tFkWTaLfJwzjzr635yP";
    
    private String accessKeySecret = "3Q0dX94NW5ReSEUuie2jKFQ6wufmnd";
    
    private String bucketName = "helltractor";
    
}
